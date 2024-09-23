package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.BatchDocRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.service.BatchDocService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Batch Doc Controller")
@RequestMapping("/{categoryId}/batch/{batchId}/document")
public class BatchDocController {

    private BatchDocService service;

    @PostMapping
    public ResponseEntity<?> createBatchDoc(@Valid @RequestBody BatchDocRequestDto dto) {
        service.createBatchDoc(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BatchDocResponseDto> getBatchDocById(@PathVariable("id") Long id) {
        BatchDocResponseDto dto = service.getBatchDocById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<BatchDocResponseDto>> getAllBatchDocs(@PathVariable Long batchId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<BatchDocResponseDto> batchDocs = service.getAllBatchDocsByBatchId(batchId, pageNumber, pageSize);
        return new ResponseEntity<>(batchDocs, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateBatchDoc(@PathVariable("id") Long id,
                                                        @RequestBody @Valid BatchDocRequestDto dto) {
        dto.setId(id);
        service.updateBatchDoc(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBatchDoc(@PathVariable("id") Long id) {
        service.deleteBatchDoc(id);
        return new ResponseEntity<>("BatchDoc successfully deleted!", HttpStatus.OK);
    }
}
