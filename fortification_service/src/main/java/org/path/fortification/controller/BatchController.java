package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.BatchRequestDto;
import org.path.fortification.dto.requestDto.EntityStateRequestDTO;
import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.BatchListResponseDTO;
import org.path.fortification.dto.responseDto.BatchResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StateResponseDto;
import org.path.fortification.enums.ActionType;
import org.path.fortification.enums.EventTest;
import org.path.fortification.enums.SampleTestResult;
import org.path.fortification.service.BatchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Batch Controller")
@RequestMapping("/{categoryId}/batch")
public class BatchController {

    private BatchService batchService;


    @PostMapping
    public ResponseEntity<?> createBatch(@PathVariable Long categoryId, @Valid @RequestBody BatchRequestDto batchRequestDto) {
        Long id = batchService.createBatch(batchRequestDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BatchResponseDto> getBatchById(@PathVariable Long categoryId, @PathVariable("id") Long id) {
        BatchResponseDto dto = batchService.getBatchById(categoryId, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("list")
    public ResponseEntity<ListResponse<BatchListResponseDTO>> getAllBatches(@PathVariable Long categoryId,
                                                                            @RequestParam(required = false) Integer pageNumber,
                                                                            @RequestParam(required = false) Integer pageSize,
                                                                            @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<BatchListResponseDTO> batches = batchService.getAllBatches(categoryId, pageNumber, pageSize, searchListRequest);
        return new ResponseEntity<>(batches, HttpStatus.OK);
    }

    @PostMapping("inventory")
    public ResponseEntity<ListResponse<BatchListResponseDTO>> getBatchInventory(@PathVariable Long categoryId,
                                                                        @RequestParam(required = false) Integer pageNumber,
                                                                        @RequestParam(required = false) Integer pageSize,
                                                                        @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<BatchListResponseDTO> batches = batchService.getBatchInventory(categoryId, pageNumber, pageSize, searchListRequest);
        return new ResponseEntity<>(batches, HttpStatus.OK);
    }


    @PostMapping("/{batchId}/state/{actionType}")
    public ResponseEntity<Boolean> updateBatchStatus(@RequestBody(required = false) EntityStateRequestDTO entityStateRequestDTO,
                                                     @PathVariable Long categoryId,
                                                     @PathVariable ActionType actionType,
                                                     @RequestParam(required = false) SampleTestResult sampleTestResult) {
        boolean isUpdated = batchService.updateBatchStatus(categoryId, entityStateRequestDTO, actionType, sampleTestResult);
        return new ResponseEntity<>(isUpdated, HttpStatus.OK);
    }

    @GetMapping("/{batchId}/actions/{actionType}")
    public ResponseEntity<List<StateResponseDto>> getBatchActions(@PathVariable(required = false) Long batchId,
                                                                  @PathVariable Long categoryId,
                                                                  @PathVariable ActionType actionType,
                                                                  @RequestParam(required = false) String sampleState) {
        List<StateResponseDto> stateResponseDtos = batchService.getBatchActions(categoryId, batchId, actionType, sampleState);
        return new ResponseEntity<>(stateResponseDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateBatch(@PathVariable Long categoryId, @PathVariable("id") Long id,
                                                       @RequestBody @Valid BatchRequestDto dto) {
        dto.setId(id);
        batchService.updateBatch(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBatch(@PathVariable Long categoryId, @PathVariable("id") Long id) {
        batchService.deleteBatch(id);
        return new ResponseEntity<>("Batch successfully deleted!", HttpStatus.OK);
    }
    @GetMapping("/history/{batchId}")
    public ResponseEntity<?> getHistoryForBatch( @PathVariable Long categoryId,@PathVariable Long batchId){
        return ResponseEntity.ok(batchService.getHistoryForBatch(batchId));
    }

    @GetMapping("/code/{uuid}/details")
    public ResponseEntity<BatchResponseDto> getDetailsForUUID(@PathVariable String uuid) {
        return new ResponseEntity<>(batchService.getDetailsForUUID(uuid), HttpStatus.OK);
    }

    @GetMapping("/{batchId}/lab-access")
    public ResponseEntity<Boolean> checkLabAccess(@PathVariable Long categoryId, @PathVariable Long batchId) {
        return new ResponseEntity<>(batchService.checkLabAccess(batchId), HttpStatus.OK);
    }

    @PutMapping("{id}/update/inspection-status")
    public ResponseEntity<?> updateBatchInspectionStatus(@PathVariable("id") Long batchId,
                                                         @RequestParam Boolean isBlocked,
                                                         @PathVariable String categoryId) {
        batchService.updateBatchInspectionStatus(batchId, isBlocked);
        return new ResponseEntity<>("Successfully Updated Inspection Status", HttpStatus.OK);
    }


    @GetMapping("/batch-search")
    public ResponseEntity<?>getFilteredBatch(@RequestParam String search){
        return new ResponseEntity<>(batchService.getFilteredBatch(search),HttpStatus.OK);
    }

    @GetMapping("/event/{batchId}")
    public ResponseEntity<?> getBathByIdForEventUpdate(@PathVariable("batchId") Long id, @PathVariable Long categoryId) {
        return ResponseEntity.ok(batchService.getBatchByIdForEventUpdate(id));
    }

    @PostMapping("/event/test/{param}")
    public ResponseEntity<?> acceptEventUpdate(@RequestBody String encrypted, @PathVariable Long categoryId, @PathVariable EventTest param) {
        batchService.eventUpdateBody(encrypted, param);
        return ResponseEntity.ok("accepted");
    }

}
