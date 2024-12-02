package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.LabSampleRequestDTO;
import org.path.lab.dto.requestDto.LabTestRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.*;
import org.path.lab.entity.*;
import org.path.lab.manager.LabSampleManager;
import org.path.lab.manager.LabTestReferenceMethodManager;
import org.path.lab.manager.MessageManager;
import org.path.lab.manager.SampleStateManager;
import org.path.lab.enums.LabSampleActionType;
import org.path.lab.enums.LabSampleResult;
import org.path.lab.enums.SampleType;
import org.path.lab.helper.Constants;
import org.path.lab.helper.RestHelper;
import org.path.lab.helper.ServiceUtils;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.LabSampleService;
import org.path.lab.service.LabService;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class LabSampleServiceImpl implements LabSampleService {
    private static final Logger logger = LoggerFactory.getLogger(LabSampleServiceImpl.class);

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);
    private final LabSampleManager labSampleManager;
    private final RestHelper restHelper;
    private final SampleStateManager sampleStateManager;
    private final MessageManager messageManager;
    private final LabTestReferenceMethodManager labTestReferenceMethodManager;


    private static final Map<String,String> actionMap;
     static {
         actionMap = new HashMap<>();
         actionMap.put("self tested lot", "selfCertified");
         actionMap.put("receive sample","inProgress");
         actionMap.put("submit test reports","done");
         actionMap.put("reject sample without testing","rejected");
     }
    private static final Map<String,String> stateMap;
    static {
        stateMap = new HashMap<>();
        stateMap.put("selfTestedLot", "selfCertified");
        stateMap.put("sentBatchSampleToLabTest","toReceive");
        stateMap.put("batchSampleInLab","inProgress");
        stateMap.put("lotSampleInLab","inProgress");
        stateMap.put("sentLotSampleToLabTest","toReceive");
    }

    private static final Map<String,String> prevStateMap;
    static {
        prevStateMap = new HashMap<>();
        prevStateMap.put("inProgress","toReceive");
        prevStateMap.put("done","inProgress");
        prevStateMap.put("rejected","inProgress");
    }

    @Autowired
    private KeycloakInfo keycloakInfo;

    private final LabService labService;

    void setKeycloakInfo(KeycloakInfo keycloakInfo) {
        this.keycloakInfo = keycloakInfo;
    }

    @Override
    public ListResponse<LabSampleResponseDto> getAllLabSamples(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        this.setSampleState(searchRequest);
        Long labId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("labId", 0).toString());
        List<LabSample> labSamples = labSampleManager.findAll(labId, pageNumber, pageSize, searchRequest);
        Long count = labSampleManager.getCount(labSamples.size(), labId, pageNumber, pageSize, searchRequest);
        return ListResponse.from(labSamples, mapper::mapEntityToDtoLabSample, count);
    }

    public void setSampleState(SearchListRequest searchRequest){
        Map<String, Object> map = new HashMap<>();
        List<String> stateNames;
        if(searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }else {
            return;
        }
        try {
            stateNames = ((List<String>) map.get("stateIds"));
            if (stateNames==null || stateNames.isEmpty())return;
            List<String> stateMapNames = stateNames.stream().map(stateMap::get).toList();
            List<SampleState> states = sampleStateManager.findAllByNames(stateMapNames);
            List<Integer> ids = states.stream().map(SampleState::getId).map(Long::intValue).toList();
            searchRequest.getSearchCriteriaList().forEach(s->{
                if (Objects.equals("stateIds",s.getKey()))s.setValue(ids);
            });
        } catch (Exception e) {
            logger.info("No stateNames present");
        }

    }

    @Override
    public LabSampleCreateResponseDto createLabSample(LabSampleRequestDTO labSampleRequestDTO,Boolean self) {
        if(labSampleRequestDTO.getReceivedDate()!=null && labSampleRequestDTO.getReceivedDate().before(labSampleRequestDTO.getSampleSentDate())){
            throw new ValidationException("Sample Received Date cannot be before Sample Sent Date");
        }
        if(labSampleRequestDTO.getReceivedDate()!=null && labSampleRequestDTO.getTestDate()!=null && labSampleRequestDTO.getTestDate().before(labSampleRequestDTO.getReceivedDate())){
            throw new ValidationException("Sample Test Date cannot be before Sample Receive Date");
        }
        LabSample entity = mapper.mapDtoToEntityLabSample(labSampleRequestDTO);
        entity.setPercentageCategoryMix(1.0);
        entity.getSampleProperties().forEach(d -> d.setLabSample(entity));
        entity.getLabTests().forEach(d -> d.setLabSample(entity));
        entity.getSampleTestDocuments().forEach( d->{
            if(d.getCategoryDocumentRequirement() != null && d.getCategoryDocumentRequirement().getId() == null) {
                d.setCategoryDocumentRequirement(null);
            }
            d.setLabSample(entity);
        });
        SampleState sampleState;
        if(self != null && self) {
            sampleState = sampleStateManager.findByName("selfCertified");
        } else {
            sampleState = sampleStateManager.findByName("toReceive");
        }
        entity.setState(sampleState);
        if (self!=null && self) {
            entity.setLab(null);

            LabSampleResult result = null;
            if (labSampleRequestDTO.getLabTests() != null && !labSampleRequestDTO.getLabTests().isEmpty()){
                result = findLabTestResult(labSampleRequestDTO.getLabTests(), labSampleRequestDTO.getPercentageCategoryMix());
            }

            Set<LabTest> labTests = mapper.mapDtoToEntityLabTestEntity(labSampleRequestDTO.getLabTests());
            entity.setLabTests(labTests.stream().peek(l -> l.setLabSample(entity)).collect(Collectors.toSet()));
            LabSample labSample =  labSampleManager.create(entity);
            return new LabSampleCreateResponseDto(labSample.getId(),null, result);
        }
        Map<String,Object> userMap = keycloakInfo.getUserInfo();
        long manufacturerId =  Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        String location;
        LabResponseDTO nearestLab;
        if(manufacturerId!=0L && manufacturerId != entity.getManufacturerId()) {
            throw new ValidationException("Can't create Sample for other organization");
        }
        if(labSampleRequestDTO.getLabId()!=null && labSampleRequestDTO.getLabId()!=0L){
            entity.setLab(new Lab(labSampleRequestDTO.getLabId()));
        }else{
            if (manufacturerId!=0L){
                location = String.valueOf(userMap.get("manufacturerAddress"));
                nearestLab = labService.getNearestLab(location, labSampleRequestDTO.getCategoryId(), null);
            }else {
                location = getManufacturerLocation(labSampleRequestDTO.getManufacturerId());
                nearestLab = labService.getNearestLab(location, labSampleRequestDTO.getCategoryId(), labSampleRequestDTO.getManufacturerId());
            }
            entity.setLab(new Lab(nearestLab.getId()));
        }

        LabSample labSample =  labSampleManager.create(entity);
        messageManager.send(labSample.getManufacturerId(),
                labSample.getCategoryId(),
                labSample.getLab().getId(),
                LabSampleActionType.create,
                sampleState,
                keycloakInfo.getAccessToken(),
                getYearFromDate(labSample.getSampleSentDate())
        );
        return new LabSampleCreateResponseDto(labSample.getId(),labSample.getLab().getId(), null);
    }

    private String getManufacturerLocation(Long manufacturerId) {
        String url = Constants.IAM_BASE_URL+ "address/manufacturer/" + manufacturerId;
        String result = null;
        try {
            AddressResponseDTO address = restHelper.fetchResponse(url, AddressResponseDTO.class, keycloakInfo.getAccessToken());
            AddressLocationResponseDto addressDto = mapper.mapToDto(address);
            result = new ObjectMapper().writeValueAsString(addressDto);
        } catch (Exception e){
            logger.info("Error while fetching address {{}}", e.getMessage());
        }
        return result;
    }

    @Override
    public LabSampleResponseDto getLabSampleById(Long labSampleId) {
        LabSample entity = labSampleManager.findById(labSampleId);
        return mapper.mapEntityToDtoLabSample(entity);

    }

    @Override
    public LabSampleResultResponseDto updateLabSample(LabSampleRequestDTO labSampleRequestDTO) {
        if(labSampleRequestDTO.getReceivedDate()!=null && labSampleRequestDTO.getReceivedDate().before(labSampleRequestDTO.getSampleSentDate())){
            throw new ValidationException("Sample Received Date cannot be before Sample Sent Date");
        }
        if(labSampleRequestDTO.getTestDate()!=null && labSampleRequestDTO.getTestDate().before(labSampleRequestDTO.getReceivedDate())){
            throw new ValidationException("Sample Test Date cannot be before Sample Receive Date");
        }
        LabSample previousLabSample = labSampleManager.findById(labSampleRequestDTO.getId());
        LabSample entity = mapper.mapDtoToEntityLabSample(labSampleRequestDTO, previousLabSample);
        LabSampleResult labSampleResult =  findLabTestResult(labSampleRequestDTO.getLabTests(), entity.getPercentageCategoryMix());
        entity = mapper.mapDtoToEntityLabSample(labSampleRequestDTO, previousLabSample);
        labSampleManager.update(entity);

        Boolean isExternalTest = previousLabSample.getInspection() != null && previousLabSample.getInspection().getIsExternalTest();
        Long labId = isExternalTest ? previousLabSample.getLab().getId() : null;
        return LabSampleResultResponseDto.builder()
                .labSampleResult(labSampleResult)
                .isExternalTest(isExternalTest)
                .labId(labId)
                .build();
    }

    private LabSampleResult findLabTestResult(Set<LabTestRequestDTO> labTests, Double percentageCategoryMix){
        Map<String,LabSampleResult> result = new HashMap<>();
        result.put("result",LabSampleResult.TEST_PASSED);
        List<Long>labTestReferenceMethodIds = labTests.stream()
            .map(LabTestRequestDTO::getLabTestReferenceMethodId)
            .filter(Objects::nonNull).toList();
        List<LabTestReferenceMethod> labTestReferenceMethods =labTestReferenceMethodManager.findAllByIds(labTestReferenceMethodIds);

        for(LabTestRequestDTO c: labTests){
            Optional<LabTestReferenceMethod> referenceMethod =labTestReferenceMethods.stream()
                .filter(refMethod->c.getLabTestReferenceMethodId()!=null && c.getLabTestReferenceMethodId().equals(refMethod.getId())).findFirst();
            c.setTestResult(LabSampleResult.TEST_PASSED);
            c.setIsMandatory(false);
            if(referenceMethod.isPresent()) {
                if (referenceMethod.get().getLabTestType().getIsMandatory()) {
                    c.setIsMandatory(true);
                    if (referenceMethod.get().getMaxValue() == null && referenceMethod.get().getMinValue() == null) {
                        if (referenceMethod.get().getDefaultPresent()!=null && !Objects.equals(referenceMethod.get().getDefaultPresent(),
                            c.getValue())) {
                            result.put("result", LabSampleResult.TEST_FAILED);
                            c.setTestResult(LabSampleResult.TEST_FAILED);
                            logger.info("failed at : {}", c);
                            logger.info("failed at : {}", referenceMethod);
                        }
                    } else if (referenceMethod.get().getName().equals("Foreign matter") ||  !(referenceMethod.get().getMaxValue() == 0D && referenceMethod.get().getMinValue() == 0D)) {
                        double minValue = referenceMethod.get().getMinValue();
                        double maxValue = referenceMethod.get().getMaxValue();
                        Double maxVariation = referenceMethod.get().getMaxPercentVariationAllowed();
                        if(maxVariation != null && maxVariation != 0D){
                            maxValue *= (100D + maxVariation) * 0.01D;
                        }
                        Double minVariation = referenceMethod.get().getMinPercentVariationAllowed();
                        if(minVariation != null && minVariation != 0D){
                            minValue *= (100D - minVariation) * 0.01D;
                        }
                        if(percentageCategoryMix != null && percentageCategoryMix != 0D){
                            minValue = minValue / percentageCategoryMix;
                            maxValue = maxValue / percentageCategoryMix;
                        }
                        if (Double.parseDouble(c.getValue()) < minValue || Double.parseDouble(c.getValue()) > maxValue) {
                            result.put("result", LabSampleResult.TEST_FAILED);
                            c.setTestResult(LabSampleResult.TEST_FAILED);
                        }
                    }

                }
            }
        }
        return result.get("result");
    }

    @Override
    public void deleteLabSample(Long labSampleId) {
        LabSample labSample = labSampleManager.delete(labSampleId);
        messageManager.send(labSample.getManufacturerId(), labSample.getCategoryId(), labSample.getLab().getId()
                , LabSampleActionType.delete, labSample.getState(), keycloakInfo.getAccessToken(), getYearFromDate(labSample.getSampleSentDate()));
    }

    @Override
    public ListResponse<LabSampleResponseDto> getAllLabSamplesByBatchId(Long batchId) {
        List<LabSample> labSamples;
        Boolean access;
        if (isInspectionUser()){
            labSamples = labSampleManager.findAllSamplesByBatchIdForInspection(batchId);
            if (labSamples.isEmpty())return new ListResponse<>();
            access = true;
        } else {
            labSamples = labSampleManager.findAllSamplesByBatchId(batchId);
            if (labSamples.isEmpty())return new ListResponse<>();
            String url = String.format("%s%s/batch/%s/lab-access", Constants.FORTIFICATION_BASE_URL,labSamples.get(0).getCategoryId(),batchId);
            access = restHelper.checkAccess(url, keycloakInfo.getAccessToken());
        }
        List<LabSample> filteredList = labSamples.stream().filter(l ->(!Objects.isNull(l.getLabTests()) && !l.getLabTests().isEmpty())).toList();
        if (access && !filteredList.isEmpty())return ListResponse.from(filteredList, mapper::mapEntityToDtoLabSample, (long) filteredList.size());
        return new ListResponse<>();
    }


    @Override
    public ListResponse<LabSampleResponseDto> getAllLabSamplesByLotId(Long lotId) {
        List<LabSample> labSamples;
        Boolean access;
        if(isInspectionUser()){
            labSamples = labSampleManager.findAllSamplesByLotIdForInspection(lotId);
            if (labSamples.isEmpty())return new ListResponse<>();
            access = true;
        }else{
            labSamples = labSampleManager.findAllSamplesByLotId(lotId);
            if(labSamples.isEmpty()) return new ListResponse<>();
            String url = String.format("%s%s/lot/%s/lab-access",Constants.FORTIFICATION_BASE_URL,labSamples.get(0).getCategoryId(),lotId);
            access = restHelper.checkAccess(url, keycloakInfo.getAccessToken());
        }
        List<LabSample> filteredList = labSamples.stream().filter(l ->(!Objects.isNull(l.getLabTests()) && !l.getLabTests().isEmpty())).toList();
        if (access && !filteredList.isEmpty())return ListResponse.from(filteredList, mapper::mapEntityToDtoLabSample, (long) filteredList.size());
        return new ListResponse<>();
    }

    @Override
    public Map<Long, LabNameAddressResponseDto> getAllLabSamplesByLotIds(SampleType sampleType, List<Long> ids) {
        List<LabSample> labSamples;
        if(isInspectionUser()){
            if(sampleType.equals(SampleType.lot)){
                labSamples = labSampleManager.findAllSamplesByLotIdsForInspection(ids);
            }else{
                labSamples =labSampleManager.findAllSamplesByBatchIdsForInspection(ids);
            }
        }else{
            if(sampleType.equals(SampleType.lot)) {
                labSamples = labSampleManager.findAllSamplesByLotIds(ids);
            }else {
                labSamples = labSampleManager.findAllSamplesByBatchIds(ids);
            }
        }

        if (labSamples.isEmpty())return new HashMap<>();
        return labSamples.stream()
                .collect(Collectors.toMap(labSample -> {
                    if(sampleType.equals(SampleType.lot)){
                        return labSample.getLotId();
                    }else{
                        return labSample.getBatchId();
                    }
                }, labSample -> {
                    LabNameAddressResponseDto responseDto = new LabNameAddressResponseDto();
                    responseDto.setId(labSample.getLab().getId());
                    responseDto.setName(labSample.getLab().getName());
                    responseDto.setLabCertificateNumber(labSample.getLab().getCertificateNo());
                    return responseDto;
                }));
    }

    @Override
    public void updateSampleStatus(Long categoryId, Long sampleId, String action, String dateOfReceiving) {
        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfReceiving);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(date!=null && date.after(new Date())){
            throw new ValidationException("Received Date cannot be Future Date");
        }
        LabSample labSample = labSampleManager.findByCategoryIdAndId(categoryId, sampleId);
        String stateName = actionMap.get(action.toLowerCase());
        if(labSample.getState().getName().equals("rejected") ||
                labSample.getState().getName().equals("done") ||
                labSample.getState().getName().equals("selfCertified") ||
                !prevStateMap.get(stateName).equals(labSample.getState().getName())){
            throw new CustomException("Cannot update the state of this lab sample", HttpStatus.BAD_REQUEST);
        }
        SampleState state = sampleStateManager.findByName(stateName);
        labSample.setState(state);
        if(date!=null){
            if(date.before(labSample.getSampleSentDate())){
                throw new ValidationException("Sample Received Date cannot be before Sample Sent Date");
            }
            labSample.setReceivedDate(date);
        }
        labSampleManager.update(labSample);
        messageManager.send(labSample.getManufacturerId(), labSample.getCategoryId(), labSample.getLab().getId()
                , LabSampleActionType.create, labSample.getState(), keycloakInfo.getAccessToken(), getYearFromDate(labSample.getSampleSentDate()));
    }

    private List<Long> getTestManufacturerIds() {
        String url = Constants.IAM_BASE_URL + "/manufacturer/test-manufacturers";
        List<Integer> testManufacturerIds = (List<Integer>) restHelper.fetchResponse(url, List.class, keycloakInfo.getAccessToken());
        return testManufacturerIds.stream().mapToLong(Integer::longValue).boxed().toList();
    }

    private boolean isInspectionUser() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("INSPECTION"));
    }

    public Integer getYearFromDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public LabNameAddressResponseDto getLabNameAddressByEntityId(SampleType entityType, Long entityId){
        Lab lab = labSampleManager.getLabNameAddressByEntityId(entityType, entityId);
        return new LabNameAddressResponseDto(lab.getId(), lab.getName(), ServiceUtils.getCompleteAddressForLab(lab), lab.getCertificateNo());
    }

    @Override
    public List<LabSampleResponseDto> getAllLabSamplesByBatchIdForEventUpdate(Long batchId) {
        List<LabSample> labSamples = labSampleManager.findAllSamplesByBatchIdForInspection(batchId);
        if (labSamples.isEmpty())return new ArrayList<>();
        return labSamples.stream().map(mapper::mapEntityToDtoLabSample).toList();
    }

    @Override
    public List<LabSampleResponseDto> getAllLabSamplesByLotIdForEventUpdate(Long lotId) {
        List<LabSample> labSamples = labSampleManager.findAllSamplesByLotIdForInspection(lotId);
        if (labSamples.isEmpty())return new ArrayList<>();
        return labSamples.stream().map(mapper::mapEntityToDtoLabSample).toList();
    }

    @Override
    public ListResponse<LabSampleResponseDto> getAllLabSamplesForSuperAdmins(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        List<Long> testManufacturerIds = getTestManufacturerIds();
        this.setSampleState(searchRequest);
        List<LabSample> labSamples = labSampleManager.findAllSamplesForSuperAdmin(pageNumber, pageSize, searchRequest, testManufacturerIds);
        Long count = labSampleManager.getCountForSuperAdmin(labSamples.size(), pageNumber, pageSize, searchRequest, testManufacturerIds);
        return ListResponse.from(labSamples, mapper::mapEntityToDtoLabSample, count);
    }
}