package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.BatchPropertyRequestDto;
import org.path.fortification.dto.responseDto.BatchPropertyResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.service.BatchPropertyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Batch Property Controller")
@RequestMapping("{categoryId}/batch/{batchId}/property")
public class BatchPropertyController {

    private BatchPropertyService service;

    @PostMapping
    public ResponseEntity<?> createBatchProperty(@PathVariable Long categoryId, @PathVariable Long batchId, @Valid @RequestBody BatchPropertyRequestDto dto) {
        service.createBatchProperty(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BatchPropertyResponseDto> getBatchPropertyById(@PathVariable Long categoryId, @PathVariable Long batchId, @PathVariable("id") Long id) {
        BatchPropertyResponseDto dto = service.getBatchPropertyById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<BatchPropertyResponseDto>> getAllBatchProperties(@PathVariable Long categoryId, @PathVariable Long batchId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<BatchPropertyResponseDto> batchProperties = service.getAllBatchProperties(pageNumber, pageSize);
        return new ResponseEntity<>(batchProperties, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateBatchProperty(
            @PathVariable Long categoryId, @PathVariable Long batchId,
            @PathVariable("id") Long id, @RequestBody @Valid BatchPropertyRequestDto dto) {
        dto.setId(id);
        service.updateBatchProperty(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBatchProperty(@PathVariable Long categoryId, @PathVariable Long batchId, @PathVariable("id") Long id) {
        service.deleteBatchProperty(id);
        return new ResponseEntity<>("BatchProperty successfully deleted!", HttpStatus.OK);
    }

}
