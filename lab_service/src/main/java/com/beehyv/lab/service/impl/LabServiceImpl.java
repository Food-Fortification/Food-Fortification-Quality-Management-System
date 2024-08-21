package com.beehyv.lab.service.impl;
import com.beehyv.lab.dto.requestDto.DashboardRequestDto;
import com.beehyv.lab.dto.requestDto.LabRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.entity.*;
import com.beehyv.lab.helper.Constants;
import com.beehyv.lab.manager.LabCategoryManager;
import com.beehyv.lab.manager.LabManager;
import com.beehyv.lab.manager.LabManufacturerCategoryManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.LabService;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;


@Transactional
@Service
@AllArgsConstructor
public class LabServiceImpl implements LabService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);
    private final LabManager labManager;
    private final LabCategoryManager labCategoryManager;
    private final LabManufacturerCategoryManager labManufacturerCategoryManager;
    private final KeycloakInfo keycloakInfo;


    private EntityManager em;
    @Override
    public ListResponse<LabResponseDTO> getAllLabs(SearchListRequest searchListRequest, String search, Integer pageNumber, Integer pageSize) {
        List<Lab> labs = labManager.findAllLabs(searchListRequest, search, pageNumber, pageSize);
        Long count = labManager.getCountForAllLabs(searchListRequest, search);
        return ListResponse.from(labs, mapper::mapEntityToDtoLab, count);
    }

    @Override
    public LabResponseDTO getLabById(Long labId) {
        Lab lab = labManager.findById(labId);
        return mapper.mapEntityToDtoLab(lab);
    }

    @Override
    public Long createLab(LabRequestDTO labRequestDTO) {
        Lab lab = mapper.mapDtoToEntityLab(labRequestDTO);
        lab.getLabDocuments().forEach(d -> {
            d.setLab(lab);
        });
        if(!lab.getLabCategoryMapping().isEmpty()) {
            lab.getLabCategoryMapping().forEach(lc -> {
                lc.setLab(lab);
            });
        }
        if(!lab.getLabManufacturerCategoryMapping().isEmpty()) {
            lab.getLabManufacturerCategoryMapping().forEach(lmc -> {
                lmc.setLab(lab);
            });
        }
       Lab createdLab = labManager.create(lab);
       return createdLab.getId();
    }

    @Override
    public void updateLabById(Long labId, LabRequestDTO labRequestDTO) {
        Lab lab = labManager.findById(labId);
        labRequestDTO.setId(labId);
        if (lab != null){
            Lab lab2 = mapper.mapDtoToEntityLab(labRequestDTO);
            lab2.setUuid(lab.getUuid());
            lab2.getLabDocuments().forEach(d -> {
                d.setLab(lab2);
            });
            labManager.update(lab2);
        }
    }

    @Override
    public void deleteLabById(Long labId) {
        Lab lab = labManager.findById(labId);
        if(lab != null) {
            labManager.delete(labId);
        }
    }

    @Override
    public LabResponseDTO getNearestLab(String address, Long categoryId, Long manufacturerId) {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressLocationResponseDto locationRequest = new AddressLocationResponseDto();
        try {
          locationRequest =  objectMapper.readValue(address, AddressLocationResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Lab> labList;

        Double manufacturerLatitude = locationRequest.getLatitude();
        Double manufacturerLongitude = locationRequest.getLongitude();
        if (manufacturerId==null || manufacturerId == 0L ){
            labList = labManufacturerCategoryManager
                    .findAllLabsByManufacturerIdAndCategoryId(null, Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString()), categoryId,null, null).stream()
                    .map(LabManufacturerCategoryMapping::getLab)
                    .toList();
        }else {
            labList = labManufacturerCategoryManager
                    .findAllLabsByManufacturerIdAndCategoryId(null, manufacturerId, categoryId, null,null).stream()
                    .map(LabManufacturerCategoryMapping::getLab)
                    .toList();
        }

        if (labList.isEmpty()){
            labList = labCategoryManager.findNearestLab(locationRequest, categoryId).stream()
                    .map(LabCategory::getLab).toList();
        }

        AddressLocationResponseDto finalLocationRequest = locationRequest;
        List<Lab> labListByPinCode = labList.stream().filter(p-> p.getAddress().getPinCode().equals(finalLocationRequest.getPinCode())).collect(Collectors.toList());
        List<Lab> labListByVillage = labList.stream().filter(p-> p.getAddress().getVillage().getName().equals(finalLocationRequest.getVillage().getName())).collect(Collectors.toList());
        List<Lab> labListByDistrict = labList.stream().filter(p-> p.getAddress().getVillage().getDistrict().getName().equals(finalLocationRequest.getDistrict().getName())).collect(Collectors.toList());

        if(labListByPinCode.size()!=0){
            labList = labListByPinCode;
        } else if(labListByVillage.size()!=0) {
            labList = labListByVillage;
        } else if(labListByDistrict.size()!=0)
            labList = labListByDistrict;
        Lab requiredLab;
        if (labListByPinCode.size() != 0 || labListByVillage.size() !=0) {
            requiredLab = labList.get(new Random().nextInt(labList.size()));
        } else {
            int index = 0;
            double min = distance(labList.get(0).getAddress().getLatitude(), manufacturerLatitude, labList.get(0).getAddress().getLongitude(), manufacturerLongitude);
            for (int i = 1; i < labList.size(); i++) {
                double diff = distance(labList.get(i).getAddress().getLatitude(), manufacturerLatitude, labList.get(i).getAddress().getLongitude(), manufacturerLongitude);
                if (diff < min) {
                    index = i;
                    min = diff;
                }
            }
            requiredLab = labList.get(index);
        }
        return mapper.mapEntityToDtoLab(requiredLab);
    }

    @Override
    public ListResponse<LabListResponseDTO> getAllActiveLabsForCategory(String search, Long manufacturerId, Long categoryId, Integer pageNumber, Integer pageSize) {
        List<Lab> labList =  labManager.findAllActiveLabsForCategory(search, categoryId, pageNumber, pageSize);
        Long count = labManager.getCountForAllActiveLabsCategory(search, categoryId);
        return ListResponse.from(labList, mapper::mapEntityToLabListDto, count);

    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.sqrt(distance);
    }
    public Map<String, CategoryRoleResponseDto> getAllRoleCategoriesForLab(Long labId){
        List<LabCategory> allByLabId = labCategoryManager.findAllByLabId(labId);
        Map<String, List<Long>> stringListMap = new HashMap<>();
        List<Long>categoryIdsList = new ArrayList<>();
        allByLabId.forEach((e)->{
            categoryIdsList.add(e.getCategoryId());
        });
        stringListMap.put("LAB",categoryIdsList);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(keycloakInfo.getAccessToken());
        String url = Constants.FORTIFICATION_BASE_URL+"category/manufacturer/role-categories";
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(stringListMap), headers);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity , Map.class);
        Map<String, CategoryRoleResponseDto> result = (Map<String, CategoryRoleResponseDto>)response.getBody();
        return result;
    }

    @Override
    public ListResponse<LabDashboardResponseDto> getLabsAggregate(DashboardRequestDto dto) {
        List<Object[]> objects = labManager.getLabsAggregateByCategoryId(dto);
        List<LabDashboardResponseDto> responseDtoList = new ArrayList<>();
        for (Object[] obj : objects) {
            LabDashboardResponseDto responseDto = new LabDashboardResponseDto();
            responseDto.setId((Long) obj[0]);
            responseDto.setName((String) obj[1]);
            responseDto.setDistrict((String) obj[2]);
            responseDto.setState((String) obj[3]);
            responseDto.setSamplesReceived((Long) obj[4]);
            responseDto.setSamplesRejected((Long) obj[5]);
            responseDto.setSamplesInLab((Long) obj[6]);
            responseDto.setTestsDone((Long) obj[7]);
            responseDto.setNablCertificate((String) obj[8]);
            responseDto.setSamplesToReceive((Long)obj[9]);
            responseDtoList.add(responseDto);
        }
        return new ListResponse<>(responseDtoList,(long) responseDtoList.size());
    }

    @Override
    public ListResponse<LabSampleDetailsResponseDto> getLabSamplesDetails(DashboardRequestDto dto, Long labId, String type) {
        List<String> stateNames;
        switch (type) {
            case "samplesReceived":
                stateNames = Arrays.asList("inProgress", "done", "rejected");
                break;
            case "samplesRejected":
                stateNames = Arrays.asList("rejected");
                break;
            case "samplesInLab":
                stateNames = Arrays.asList("inProgress");
                break;
            case "testsDone":
                stateNames = Arrays.asList("done");
                break;
            case "samplesToReceive":
                stateNames = Arrays.asList("toReceive");
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
        List<LabSample> objects = labManager.getLabSamplesDetails(dto, labId, stateNames);
        List<LabSampleDetailsResponseDto> responseDtoList = new ArrayList<>();
        for (LabSample obj : objects) {
            LabSampleDetailsResponseDto responseDto = new LabSampleDetailsResponseDto();
            responseDto.setId(obj.getId());
            responseDto.setBatchId(obj.getBatchId());
            responseDto.setLotId(obj.getLotId());
            responseDto.setBatchNo(obj.getBatchNo());
            responseDto.setLotNo(obj.getLotNo());
            responseDto.setReceivedDate(obj.getReceivedDate());
            responseDto.setTestDate(obj.getTestDate());
            responseDto.setState(obj.getState());
            responseDto.setLab(mapper.mapEntityToDtoLab(obj.getLab()));
            responseDtoList.add(responseDto);
        }
        return new ListResponse<>(responseDtoList,(long) responseDtoList.size());

    }

    @Override
    public String findCertificateByName(String name) {
        String certificateNo=labManager.findCertificateByName(name);
        return certificateNo;
    }
}
