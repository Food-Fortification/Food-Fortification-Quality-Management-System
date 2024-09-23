package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.requestDto.StateLabTestAccessRequestDto;

import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.StateLabTestAccessResponseDto;
import com.beehyv.iam.enums.EntityType;

import com.beehyv.iam.enums.StateLabTestAccessType;
import com.beehyv.iam.service.StateLabTestAccessService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/state-lab-test-access")
@Tag(name = "State Lab Test Access Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class StateLabTestAccessController {
    private final StateLabTestAccessService stateLabTestAccessService;

    @GetMapping("/{categoryId}/{entityType}/{stateId}")
    public ResponseEntity<?> getByStateAndCategoryAndEntityType(@PathVariable("categoryId") Long categoryId ,
                                                                @PathVariable("entityType") EntityType entityType,
                                                                @PathVariable("stateId") Long stateId ){
        return ResponseEntity.ok(stateLabTestAccessService.getByStateAndCategoryAndEntityType(categoryId,entityType,stateId));
    }

    @GetMapping("manufacturer/{manufacturerId}/{categoryId}/{entityType}/{stateLabTestAccessType}")
    public ResponseEntity<?> getByManufacturerAndCategoryAndEntityType(@PathVariable Long manufacturerId,
                                                                       @PathVariable("categoryId") Long categoryId ,
                                                                       @PathVariable("entityType") EntityType entityType,
                                                                       @PathVariable("stateLabTestAccessType") StateLabTestAccessType stateLabTestAccessType){
        return ResponseEntity.ok(stateLabTestAccessService.findByStateAndCategoryAndEntityType(manufacturerId,categoryId,entityType,stateLabTestAccessType));
    }

    @GetMapping("manufacturer/{manufacturerId}/{categoryId}/{entityType}")
    public ResponseEntity<?> getIsManufacturerRawMaterialsProcured(@PathVariable Long manufacturerId,
                                                                       @PathVariable("categoryId") Long categoryId ,
                                                                       @PathVariable("entityType") EntityType entityType){
        return ResponseEntity.ok(stateLabTestAccessService.getIsManufacturerWarehouseStateProcured(manufacturerId,categoryId,entityType));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody StateLabTestAccessRequestDto dto){
       stateLabTestAccessService.create(dto);
        return new ResponseEntity<>("Successfully Created entity", HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}/{entityType}/{stateId}")
    public ResponseEntity<?> update(@PathVariable("categoryId") Long categoryId , @PathVariable("entityType") EntityType entityType,
                                    @PathVariable("stateId") Long stateId,
                                    @Valid @RequestBody StateLabTestAccessRequestDto dto){
        stateLabTestAccessService.update(dto,categoryId,entityType,stateId);

        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}/{entityType}/{stateId}")
    public ResponseEntity<?> delete(@PathVariable("categoryId") Long categoryId , @PathVariable("entityType") EntityType entityType, @PathVariable("stateId") Long stateId){
        stateLabTestAccessService.delete(categoryId,entityType,stateId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/get-all")
    public ResponseEntity<?> getAllUsers(@RequestBody(required = false) SearchListRequest searchListRequest,
                                         @RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize){
        ListResponse<StateLabTestAccessResponseDto> allStateLabTestAccess = stateLabTestAccessService.getAllStateLabTestAccess(searchListRequest, pageNumber, pageSize);
        return ResponseEntity.ok(allStateLabTestAccess);
    }
}
