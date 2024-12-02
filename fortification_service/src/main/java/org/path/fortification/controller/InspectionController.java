package org.path.fortification.controller;


import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.BatchListResponseDTO;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.LotListResponseDTO;
import org.path.fortification.service.InspectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Inspection Controller")
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    @PostMapping("/{categoryId}/batch/list")
    public ResponseEntity<ListResponse<BatchListResponseDTO>> getAllBatches(@PathVariable Long categoryId,
                                                                            @RequestParam(required = false) Integer pageNumber,
                                                                            @RequestParam(required = false) Integer pageSize,
                                                                            @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<BatchListResponseDTO> batches = inspectionService.getAllBatches(categoryId, searchListRequest, pageNumber, pageSize);
        return new ResponseEntity<>(batches, HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/lot/list")
    public ResponseEntity<ListResponse<LotListResponseDTO>> getAllLots(@PathVariable Long categoryId,
                                                                       @RequestParam(required = false) Integer pageNumber,
                                                                       @RequestParam(required = false) Integer pageSize,
                                                                       @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<LotListResponseDTO> lots = inspectionService.getAllLots(categoryId, searchListRequest, pageNumber, pageSize);
        return new ResponseEntity<>(lots, HttpStatus.OK);
    }
}
