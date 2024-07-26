package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.MixMappingCommentsRequestDto;
import com.beehyv.fortification.dto.requestDto.MixMappingRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.MixMappingResponseDto;
import com.beehyv.fortification.service.MixMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Batch Usage Controller")
@RequestMapping("/{categoryId}/batch/{batchId}/usage")
public class MixMappingController {

    private MixMappingService service;

    @PostMapping
    public ResponseEntity<?> createMixMapping(
            @PathVariable Long categoryId, @PathVariable Long batchId, @Valid @RequestBody MixMappingRequestDto dto) {
        service.createMixMapping(batchId, dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MixMappingResponseDto> getMixMappingById(
            @PathVariable Long categoryId, @PathVariable Long batchId, @PathVariable("id") Long id) {
        MixMappingResponseDto dto = service.getMixMappingById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<MixMappingResponseDto>> getAllMixMappingsForCategoryAndSourceBatch(
            @PathVariable Long categoryId, @PathVariable Long batchId,@RequestParam(required = false) Integer pageNumber,@RequestParam(required = false) Integer pageSize) {
        ListResponse<MixMappingResponseDto> mixMappings = service.getAllMixMappingsByTargetBatch(batchId, pageNumber, pageSize);
        return new ResponseEntity<>(mixMappings, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMixMapping(@PathVariable Long categoryId, @PathVariable Long batchId, @PathVariable("id") Long id,
                                                                 @RequestBody @Valid MixMappingRequestDto dto) {
        dto.setId(id);
        service.updateMixMapping(batchId, dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMixMapping(@PathVariable Long categoryId, @PathVariable Long batchId, @PathVariable("id") Long id) {
        service.deleteMixMapping(id);
        return new ResponseEntity<>("MixMapping successfully deleted!", HttpStatus.OK);
    }
    @PutMapping("/update-mixes")
    public ResponseEntity<?> updateBatchMixes(@RequestBody MixMappingCommentsRequestDto mixesInformation, @PathVariable Long categoryId, @PathVariable Long batchId){
        service.updateBatchMixes(mixesInformation,batchId);
        return new ResponseEntity<>("Successfully Updated Mixes",HttpStatus.OK);
    }

}
