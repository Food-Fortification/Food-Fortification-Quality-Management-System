package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.requestDto.StateLabTestAccessRequestDto;

import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.StateLabTestAccessResponseDto;
import com.beehyv.iam.enums.EntityType;
import com.beehyv.iam.enums.StateLabTestAccessType;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.manager.StateLabTestAccessManager;
import com.beehyv.iam.manager.StatusManager;
import com.beehyv.iam.model.*;
import com.beehyv.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class StateLabTestAccessService {
    private final StateLabTestAccessManager stateLabTestAccessManager;
    private final ManufacturerManager manufacturerManager;
    private final DtoMapper dtoMapper;
    private final StatusManager statusManager;

    public void create(StateLabTestAccessRequestDto dto){
       StateLabTestAccess stateLabTestAccess = dtoMapper.mapToEntity(dto);
        if(stateLabTestAccess.getStatus().getId()==null) {
            Status status = statusManager.findByName("Active");
            stateLabTestAccess.setStatus(status);

        }
        stateLabTestAccessManager.create(stateLabTestAccess);
    }

    public void update(StateLabTestAccessRequestDto dto,Long categoryId, EntityType entityType, Long stateId){
        StateLabTestAccess existingStateLabTestAccess =stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId,entityType,stateId);

        if(dto.getStatusId() !=null){
           Status status = statusManager.findById(dto.getStatusId());
            existingStateLabTestAccess.setStatus(status);
        }
        if(dto.getLabSelectionAllowed() !=null){

            existingStateLabTestAccess.setLabSelectionAllowed(dto.getLabSelectionAllowed());
        }
        if(dto.getBlockWorkflowForTest() !=null){
            existingStateLabTestAccess.setBlockWorkflowForTest(dto.getBlockWorkflowForTest());
        }

        stateLabTestAccessManager.update(existingStateLabTestAccess);


    }

    public StateLabTestAccessResponseDto getByStateAndCategoryAndEntityType(Long categoryId, EntityType entityType, Long stateId){
        StateLabTestAccess stateLabTestAccess = stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId,entityType,stateId);
        return dtoMapper.mapToDto(stateLabTestAccess);
    }
    public Boolean findByStateAndCategoryAndEntityType(Long manufacturerId, Long categoryId, EntityType entityType, StateLabTestAccessType stateLabTestAccessType){
        Manufacturer manufacturer= manufacturerManager.findById(manufacturerId);
        Long manufacturerStateId = manufacturer.getAddress().getVillage().getDistrict().getState().getId();
        StateLabTestAccess stateLabTestAccess = stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId, entityType, manufacturerStateId);
        if (stateLabTestAccess == null && !stateLabTestAccessType.equals(StateLabTestAccessType.RAWMATERIALS_PROCURED)){
            return true;
        } else if(stateLabTestAccess == null) {
            return false;
        }
        if(stateLabTestAccessType.equals(StateLabTestAccessType.LAB_SELECTION)){
            return true; // true for fssai flow
//            return stateLabTestAccess.getLabSelectionAllowed();
        } else if (stateLabTestAccessType.equals(StateLabTestAccessType.RAWMATERIALS_PROCURED)) {
            if (manufacturer.getIsRawMaterialsProcured()) return stateLabTestAccess.getIsStateProcuredRawMaterials();
            return false;
        }else {
            return stateLabTestAccess.getBlockWorkflowForTest();
        }
    }

    public Boolean getIsManufacturerWarehouseStateProcured(Long manufacturerId, Long categoryId, EntityType entityType){
        Manufacturer manufacturer= manufacturerManager.findById(manufacturerId);
        Long manufacturerStateId = manufacturer.getAddress().getVillage().getDistrict().getState().getId();
        StateLabTestAccess stateLabTestAccess = stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId, entityType, manufacturerStateId);
        return stateLabTestAccess != null && manufacturer.getIsRawMaterialsProcured() && stateLabTestAccess.getIsStateProcuredRawMaterials();
    }

    public void delete(Long categoryId,EntityType entityType,Long stateId){
        stateLabTestAccessManager.deleteEntity(categoryId,entityType,stateId);
    }


    public ListResponse<StateLabTestAccessResponseDto> getAllStateLabTestAccess(SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize) {
        List<StateLabTestAccess> entities;
        entities = stateLabTestAccessManager.findAllStateLabTestAccessBySearchAndFilter(searchListRequest, pageNumber, pageSize);
        Long count = stateLabTestAccessManager.getCount(searchListRequest);
        return ListResponse.from(entities,dtoMapper::mapToDto,count);
    }

}
