package com.beehyv.fortification.controller;


import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.LotListResponseDTO;
import com.beehyv.fortification.dto.responseDto.LotResponseDto;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.service.LotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Lot Controller")
@RequestMapping("/{categoryId}/lot")
public class LotController {

    private final LotService service;

    @PostMapping
    public ResponseEntity<?> createLot(@Valid @RequestBody LotRequestDto dto, @PathVariable Long categoryId) {
        return new ResponseEntity<>(service.createLot(categoryId, dto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<LotResponseDto> getLotById(@PathVariable("id") Long id, @PathVariable Long categoryId) {
        try {
            LotResponseDto dto = service.getLotById(id, categoryId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("list")
    public ResponseEntity<ListResponse<LotListResponseDTO>> getAllLots(@PathVariable Long categoryId,
                                                                       @RequestParam(required = false) Integer pageNumber,
                                                                       @RequestParam(required = false) Integer pageSize,
                                                                       @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<LotListResponseDTO> lots = service.getAllLots(categoryId, pageNumber, pageSize, searchListRequest);
        return new ResponseEntity<>(lots, HttpStatus.OK);
    }

    @GetMapping("/history/{lotId}")
    public ResponseEntity<?> getHistoryForLot(@PathVariable Long categoryId, @PathVariable Long lotId) {
        return ResponseEntity.ok(service.getHistoryForLot(lotId));
    }

    @PostMapping("inventory")
    public ResponseEntity<ListResponse<LotListResponseDTO>> getLotInventory(@PathVariable Long categoryId,
                                                                            @RequestParam(required = false) Integer pageNumber,
                                                                            @RequestParam(required = false) Integer pageSize,
                                                                            @RequestParam(required = false) Boolean approvedSourceLots,
                                                                            @RequestBody(required = false) SearchListRequest searchRequest) {
        ListResponse<LotListResponseDTO> lots = service.getLotInventory(categoryId, pageNumber, pageSize, approvedSourceLots, searchRequest);
        return new ResponseEntity<>(lots, HttpStatus.OK);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<ListResponse<LotResponseDto>> getAllLotsForBatch(@PathVariable Long batchId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize, @PathVariable Long categoryId) {
        ListResponse<LotResponseDto> lots = service.getAllLotsByBatchId(batchId, pageNumber, pageSize);
        return new ResponseEntity<>(lots, HttpStatus.OK);
    }

    @GetMapping("/source/{sourceCategoryId}")
    public ResponseEntity<ListResponse<LotResponseDto>> getAllBySourceCategoryId(@PathVariable Long categoryId,
                                                                                 @PathVariable Long sourceCategoryId,
                                                                                 @RequestParam(required = false) Long manufacturerId,
                                                                                 @RequestParam(required = false) String search,
                                                                                 @RequestParam(required = false) Integer pageNumber,
                                                                                 @RequestParam(required = false) Integer pageSize) {
        ListResponse<LotResponseDto> lots = service.getAllLotsBySourceCategoryId(sourceCategoryId, manufacturerId, search, pageNumber, pageSize);
        return new ResponseEntity<>(lots, HttpStatus.OK);
    }

    @PostMapping("/{id}/state/{actionType}")
    public ResponseEntity<Boolean> updateLotStatus(@RequestBody(required = false) EntityStateRequestDTO entityStateRequestDTO,
                                                   @PathVariable Long categoryId,
                                                   @PathVariable Long id,
                                                   @PathVariable ActionType actionType,
                                                   @RequestParam(required = false) SampleTestResult sampleTestResult,
                                                   @RequestParam(required = false) Boolean isExternalTest) {
        boolean isUpdated = service.updateLotState(categoryId, entityStateRequestDTO, actionType, sampleTestResult, isExternalTest);
        return new ResponseEntity<>(isUpdated, HttpStatus.OK);
    }

    @PostMapping("/receive")
    public ResponseEntity<Boolean> receiveLot(@RequestBody LotReceiveRequestDto dto, @PathVariable Long categoryId){
        return new ResponseEntity<>(service.receiveLot(dto, categoryId, new ArrayList<>()), HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity<Boolean> acceptLot(@RequestBody LotReceiveRequestDto dto, @PathVariable Long categoryId){
        return new ResponseEntity<>(service.acceptLot(dto, categoryId, new ArrayList<>()), HttpStatus.OK);
    }

    @GetMapping("/{id}/actions/{actionType}")
    public ResponseEntity<List<StateResponseDto>> geLotActions(@PathVariable(required = false) Long id,
                                                               @PathVariable Long categoryId,
                                                               @PathVariable ActionType actionType,
                                                               @RequestParam(required = false) String sampleState) {
        List<StateResponseDto> stateResponseDtos = service.getLotActions(categoryId, id, actionType, sampleState);
        return new ResponseEntity<>(stateResponseDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateLot(@PathVariable("id") Long id,
                                            @RequestBody @Valid LotRequestDto dto, @PathVariable Long categoryId) {
        dto.setId(id);
        service.updateLot(categoryId, dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLot(@PathVariable("id") Long id, @PathVariable Long categoryId) {
        service.deleteLot(id);
        return new ResponseEntity<>("Lot successfully deleted!", HttpStatus.OK);
    }

    @GetMapping("/code/{uuid}/details")
    public ResponseEntity<LotResponseDto> getDetailsForUUID(@PathVariable String uuid) {
        return new ResponseEntity<>(service.getDetailsForUUID(uuid), HttpStatus.OK);
    }

    @GetMapping("/{id}/lab-access")
    public ResponseEntity<Boolean> checkLabAccess(@PathVariable Long categoryId, @PathVariable Long id) {
        return new ResponseEntity<>(service.checkLabAccess(id), HttpStatus.OK);
    }

    @PutMapping("{id}/update/inspection-status")
    public ResponseEntity<?> updateBatchInspectionStatus(@PathVariable("id") Long lotId,
                                                         @RequestParam Boolean isBlocked,
                                                         @PathVariable String categoryId) {
        service.updateBatchInspectionStatus(lotId, isBlocked);
        return new ResponseEntity<>("Successfully Updated Inspection Status", HttpStatus.OK);
    }

    @PostMapping("/target")
    public ResponseEntity<?> createTargetLotFromSourceLots(@Valid @RequestBody TargetLotRequestDto dto,
                                                           @PathVariable Long categoryId,
                                                           @RequestParam(required = false) Boolean externalDispatch) {
        return new ResponseEntity<>(service.createTargetLotFromSourceLots(dto, categoryId, externalDispatch), HttpStatus.CREATED);
    }

    @GetMapping("/event/{lotId}")
    public ResponseEntity<?> getBathByIdForEventUpdate(@PathVariable("lotId") Long id, @PathVariable Long categoryId) {
        return ResponseEntity.ok(service.getLotByIdForEventUpdate(id));
    }
    @GetMapping("/{id}/target")
    private ResponseEntity<?>getTartgetManufacturerByLotId(@PathVariable Long id){
        return new ResponseEntity<>(service.getTartgetManufacturerByLotId(id), HttpStatus.OK);
    }
}
