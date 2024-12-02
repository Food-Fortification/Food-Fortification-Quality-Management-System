package org.path.iam.service;

import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.requestDto.StateLabTestAccessRequestDto;

import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.StateLabTestAccessResponseDto;
import org.path.iam.enums.EntityType;
import org.path.iam.enums.StateLabTestAccessType;
import org.path.iam.manager.ManufacturerManager;
import org.path.iam.manager.StateLabTestAccessManager;
import org.path.iam.manager.StatusManager;
import org.path.iam.model.Manufacturer;
import org.path.iam.model.StateLabTestAccess;
import org.path.iam.model.Status;
import org.path.iam.utils.DtoMapper;
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
        return stateLabTestAccess.getLabSelectionAllowed();
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
