package org.path.lab.service.impl;


import org.path.lab.dto.requestDto.InspectionRequestDTO;
import org.path.lab.dto.responseDto.AddressLocationResponseDto;
import org.path.lab.dto.responseDto.AddressResponseDTO;
import org.path.lab.dto.responseDto.InspectionResponseDTO;
import org.path.lab.dto.responseDto.LabResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.Inspection;
import org.path.lab.entity.Lab;
import org.path.lab.enums.SampleType;
import org.path.lab.helper.Constants;
import org.path.lab.helper.RestHelper;
import org.path.lab.manager.InspectionManager;
import org.path.lab.manager.SampleStateManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.InspectionService;
import org.path.lab.service.LabService;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class InspectionServiceImpl implements InspectionService {

  private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

  private final InspectionManager inspectionManager;

  private final LabService labService;

  private final RestHelper restHelper;
  private final SampleStateManager sampleStateManager;
  private final RestHelper helper;

  @Autowired
  private KeycloakInfo keycloakInfo;

  private static final Map<String,String> stateMap;
  static {
    stateMap = new HashMap<>();
    stateMap.put("Sample in Lab","inProgress");
    stateMap.put("Submit Test Reports","done");
    stateMap.put("Sample Rejected","rejected");
  }

  @Override
  public Long create(InspectionRequestDTO inspectionRequestDTO) {
    Inspection entity = mapper.mapDtoToEntityInspection(inspectionRequestDTO);
    Lab lab = entity.getLabSample().getLab();
    entity.setIsExternalTest(inspectionRequestDTO.getExternalTest() != null ? inspectionRequestDTO.getExternalTest() : false);
    if(entity.isBlocking()){
      Long categoryId = entity.getLabSample().getCategoryId();
      Long batchId = entity.getLabSample().getBatchId();
      String url = Constants.FORTIFICATION_BASE_URL + categoryId +"%s/update/inspection-status?isBlocked=true";
      if(batchId!=null){
        url = String.format(url, "/batch/" + batchId);
        helper.updateData(url,null,keycloakInfo.getAccessToken());
      }
      else{
        url = String.format(url, "/lot/" + entity.getLabSample().getLotId());
        helper.updateData(url,null,keycloakInfo.getAccessToken());
      }
    }
    Map<String,Object> userMap = keycloakInfo.getUserInfo();
    long manufacturerId =  Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0L).toString());
    String location;
    LabResponseDTO nearestLab;
    if(lab.getId()==null || lab.getId()==0L) {
      if (manufacturerId != 0L) {
        location = String.valueOf(userMap.get("manufacturerAddress"));
        nearestLab = labService.getNearestLab(location, entity.getLabSample().getCategoryId(),
            null);
      } else {
        location = getManufacturerLocation(entity.getLabSample().getManufacturerId());
        nearestLab = labService.getNearestLab(location, entity.getLabSample().getCategoryId(),
            entity.getLabSample().getManufacturerId());
      }
      entity.getLabSample().setLab(new Lab(nearestLab.getId()));
    }
    else{
      entity.getLabSample().setLab(new Lab(lab.getId()));
    }
    if (entity.getLabSample().getState().getId()==null){
      entity.getLabSample().setState(sampleStateManager.findByName("toReceive"));
    }
    entity = inspectionManager.create(entity);
    return entity.getId();
  }


  private String getManufacturerLocation(Long manufacturerId) {
    String url = Constants.IAM_BASE_URL+ "address/manufacturer/" + manufacturerId;
    String result = null;
    try {
      AddressResponseDTO address = restHelper.fetchResponse(url, AddressResponseDTO.class, keycloakInfo.getAccessToken());
      AddressLocationResponseDto addressDto = mapper.mapToDto(address);
      result = new ObjectMapper().writeValueAsString(addressDto);
    } catch (Exception e){
      log.info("Error while fetching address {{}}", e.getMessage());
    }
    return result;
  }

  @Override
  public InspectionResponseDTO getById(Long id) {
    Inspection inspection = inspectionManager.findById(id);
    if(inspection != null) {
      return mapper.mapEntityToDtoInspection(inspection);
    } else {
      return null;
    }
  }

  @Override
  public void delete(Long id) {
    inspectionManager.delete(id);
  }

  @Override
  public Long update(InspectionRequestDTO inspectionRequestDTO) {
    Inspection inspection = inspectionManager.update(mapper.mapDtoToEntityInspection(inspectionRequestDTO));
    return inspection.getId();
  }

  @Override
  public ListResponse<Long> getAllIdsBySampleType(SampleType sampleType, Long categoryId, String sampleState, Integer pageNumber, Integer pageSize) {
    List<Long> testManufacturerIds = getTestManufacturerIds();
    String state = null;
    if(sampleState!=null){
      state = stateMap.get(sampleState);
    }
    List<Long> idList = inspectionManager.findAllIdsBySampleType(sampleType, categoryId, state, pageNumber, pageSize, testManufacturerIds);
    Long count = inspectionManager.getCountForSampleType(idList.size(),sampleType, categoryId, state, pageNumber, pageSize, testManufacturerIds);
    return new ListResponse(count,idList);
  }

  @Override
  public Boolean getInspectionSampleStatus(SampleType sampleType, Long id) {
    Inspection inspection = inspectionManager.findLatestSampleById(sampleType,id);
    if(inspection == null)
      return true;
    String sampleStateName = inspection.getLabSample().getState().getName();
    if(sampleStateName.equals("rejected") || sampleStateName.equals("done") || sampleStateName.equals("selfCertified"))
      return true;
    return false;
  }

  private List<Long> getTestManufacturerIds() {
    String url = Constants.IAM_BASE_URL + "/manufacturer/test-manufacturers";
    List<Integer> testManufacturerIds = (List<Integer>) restHelper.fetchResponse(url, List.class, keycloakInfo.getAccessToken());
    return testManufacturerIds.stream().mapToLong(Integer::longValue).boxed().toList();
  }

}
