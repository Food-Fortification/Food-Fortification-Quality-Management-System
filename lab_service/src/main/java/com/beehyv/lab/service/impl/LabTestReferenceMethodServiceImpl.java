package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.entity.LabSample;
import com.beehyv.lab.entity.LabTestReferenceMethod;
import com.beehyv.lab.entity.LabTestType;
import com.beehyv.lab.helper.Constants;
import com.beehyv.lab.helper.RestHelper;
import com.beehyv.lab.manager.LabSampleManager;
import com.beehyv.lab.manager.LabTestReferenceMethodManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.LabTestReferenceMethodService;
import com.beehyv.lab.service.LabTestTypeService;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class LabTestReferenceMethodServiceImpl implements LabTestReferenceMethodService {

    private final LabTestReferenceMethodManager labTestReferenceMethodManager;
    private final LabSampleManager labSampleManager;
    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    @Autowired
    private LabTestTypeService labTestTypeService;

    @Autowired
    private RestHelper restHelper;
    
    @Autowired
    private KeycloakInfo keycloakInfo;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    public List<LabTestReferenceMethodResponseDTO> getAllLabTestReferenceMethods(Integer pageNumber, Integer pageSize) {
        List<LabTestReferenceMethod> labTestReferenceMethods = labTestReferenceMethodManager.findAll(pageNumber, pageSize);
        List<LabTestReferenceMethodResponseDTO> dtoList = new ArrayList<>();
        for(LabTestReferenceMethod labTestReferenceMethod : labTestReferenceMethods) {
            LabTestReferenceMethodResponseDTO dto = mapper.mapEntityToDtoLabTestReferenceMethod(labTestReferenceMethod);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<LabTestReferenceMethodResponseDTO> getAllLabTestReferenceMethodsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, LabTestType.Type type, Long manufacturerId) {
        String geoId = null;
        String address = keycloakInfo.getUserInfo().getOrDefault("manufacturerAddress", "").toString();

        if (!address.isEmpty()){
            try {
                ObjectMapper mapper1 = new ObjectMapper();
                AddressLocationResponseDto dto = mapper1.readValue(address, AddressLocationResponseDto.class);
                geoId = dto.getState().getGeoId();
            }catch (JsonProcessingException e){
                log.info(e.getMessage());
            }
        }else if(manufacturerId != null){
            String url = Constants.IAM_BASE_URL+ "address/manufacturer/" + manufacturerId;
            try {
                AddressResponseDTO addressResponseDTO = restHelper.fetchResponse(url, AddressResponseDTO.class, keycloakInfo.getAccessToken());
                geoId = addressResponseDTO.getVillage().getDistrict().getState().getGeoId();
            }catch (Exception e){
                log.info(e.getMessage());
            }
        }

        List<LabTestReferenceMethod> labTestReferenceMethods = labTestReferenceMethodManager.findAllByCategoryId(categoryId,pageNumber, pageSize , type, geoId);
        List<LabTestReferenceMethodResponseDTO> dtoList = new ArrayList<>();
        for(LabTestReferenceMethod labTestReferenceMethod : labTestReferenceMethods) {
            LabTestReferenceMethodResponseDTO dto = mapper.mapEntityToDtoLabTestReferenceMethod(labTestReferenceMethod);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public LabTestReferenceMethodResponseDTO getLabTestReferenceMethodById(Long labTestReferenceMethodId) {
        LabTestReferenceMethod labTestReferenceMethod = labTestReferenceMethodManager.findById(labTestReferenceMethodId);
        if(labTestReferenceMethod != null) {
            return mapper.mapEntityToDtoLabTestReferenceMethod(labTestReferenceMethod);
        } else {
            return null;
        }
    }

    @Override
    public void addLabTestReferenceMethod(LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO) {
        labTestReferenceMethodManager.create(mapper.mapDtoToEntityLabTestReferenceMethod(labTestReferenceMethodRequestDTO));

    }

    @Override
    public void updateLabTestReferenceMethodById(Long labTestReferenceMethodId, LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO) {
        LabTestReferenceMethod labTestReferenceMethod = labTestReferenceMethodManager.findById(labTestReferenceMethodId);
        labTestReferenceMethodRequestDTO.setId(labTestReferenceMethodId);
        if (labTestReferenceMethod != null){
            LabTestReferenceMethod labTestReferenceMethod2 = mapper.mapDtoToEntityLabTestReferenceMethod(labTestReferenceMethodRequestDTO);
            labTestReferenceMethod2.setUuid(labTestReferenceMethod.getUuid());
            labTestReferenceMethodManager.update(labTestReferenceMethod2);
        }
    }

    @Override
    public void deleteLabTestReferenceMethodById(Long labTestReferenceMethodId) {
        LabTestReferenceMethod labTestReferenceMethod = labTestReferenceMethodManager.findById(labTestReferenceMethodId);
        if(labTestReferenceMethod != null) {
            labTestReferenceMethodManager.delete(labTestReferenceMethodId);
        }
    }

    @Override
    public ListResponse<LabMethodResponseDto> getAllMethodsByCategoryId(Long categoryId, Long labSampleId, Integer pageNumber, Integer pageSize) {
        LabSample labSample = labSampleManager.findById(labSampleId);
        Long manufacturerId = labSample.getManufacturerId();

        String geoId = null;
        if(manufacturerId!=null && manufacturerId!=0L){
            String url = Constants.IAM_BASE_URL+ "address/manufacturer/" + manufacturerId;
            AddressResponseDTO address = restHelper.fetchResponse(url, AddressResponseDTO.class, keycloakInfo.getAccessToken());
            geoId = address.getVillage().getDistrict().getState().getGeoId();
        }

        List<LabTestTypeResponseDTO> typeDtoList = labTestTypeService.getLabTestTypesByCategoryId(categoryId, geoId, pageNumber, pageSize);
        List<LabMethodResponseDto> list = new ArrayList<>();
        for(LabTestTypeResponseDTO testTypeDto : typeDtoList) {
            List<LabTestReferenceMethod> methodList =
                    labTestReferenceMethodManager.findAllByTestTypeId(testTypeDto.getId(), pageNumber, pageSize);
            if(!methodList.isEmpty()) {
                List<LabTestReferenceMethodResponseDTO> methodDtoList = new ArrayList<>();
                for(LabTestReferenceMethod labTestReferenceMethod : methodList) {
                    LabTestReferenceMethodResponseDTO methodDto = mapper.mapEntityToDtoLabTestReferenceMethod(labTestReferenceMethod);
                    if(labSample.getPercentageCategoryMix() != null && labSample.getPercentageCategoryMix() != 0D && methodDto.getMaxValue() != null && methodDto.getMinValue() != null){
                        methodDto.setMaxValue(Double.valueOf(decfor.format(methodDto.getMaxValue() / labSample.getPercentageCategoryMix())));
                        methodDto.setMinValue(Double.valueOf(decfor.format(methodDto.getMinValue() / labSample.getPercentageCategoryMix())));
                    }
                    methodDtoList.add(methodDto);
                }
                testTypeDto.setLabTestReferenceMethods(methodDtoList);
                LabMethodResponseDto responseDto = new LabMethodResponseDto();
                responseDto.setLabTestTypeResponses(testTypeDto);
                responseDto.setLabTestReferenceMethodResponses(methodDtoList);
                list.add(responseDto);
            }
        }
        return ListResponse.from(list, d -> d, Long.parseLong(String.valueOf(list.size())));
    }
}
