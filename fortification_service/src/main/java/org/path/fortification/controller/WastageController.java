package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.WastageRequestDto;
import org.path.fortification.dto.responseDto.WastageResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.enums.WastageType;
import org.path.fortification.service.WastageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Wastage Controller")
@RequestMapping("/{categoryId}/{wastageType}/{entityId}/wastage")
public class WastageController {

    private WastageService service;

    @PostMapping
    public ResponseEntity<Long> createWastage(
            @PathVariable Long categoryId, @PathVariable Long entityId, @PathVariable("wastageType") WastageType wastageType,@Valid @RequestBody WastageRequestDto dto) {

        if(wastageType.equals(WastageType.lot)) {
            return new ResponseEntity<>(service.createLotWastage(dto, entityId), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(service.createBatchWastage(dto, entityId), HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<WastageResponseDto> getWastageById(
            @PathVariable Long categoryId, @PathVariable Long entityId, @PathVariable("id") Long id) {
        WastageResponseDto dto = service.getWastageById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<WastageResponseDto>> getAllWastes(
            @PathVariable Long categoryId, @PathVariable Long entityId,@PathVariable("wastageType") WastageType wastageType,@RequestParam(required = false) Integer pageNumber,@RequestParam(required = false) Integer pageSize) {
        if(wastageType.equals(WastageType.lot)) {
            ListResponse<WastageResponseDto> lotWastage = service.getAllWastesForLot(entityId, pageNumber, pageSize);
            return new ResponseEntity<>(lotWastage, HttpStatus.OK);

        }
        ListResponse<WastageResponseDto> batchWastage = service.getAllWastesForBatch(entityId, pageNumber, pageSize);
        return new ResponseEntity<>(batchWastage, HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateWastage(
            @PathVariable Long categoryId, @PathVariable Long entityId,@PathVariable("wastageType") WastageType wastageType,
            @PathVariable("id") Long id, @RequestBody @Valid WastageRequestDto dto) {
        dto.setId(id);
        if(wastageType.equals(WastageType.lot)) {
            service.updateLotWastage(dto, entityId);
        }
        if(wastageType.equals(WastageType.batch)) {
            service.updateBatchWastage(dto, entityId);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWastage(
            @PathVariable Long categoryId, @PathVariable Long entityId,@PathVariable("wastageType") WastageType wastageType, @PathVariable("id") Long id) {
        if(wastageType.equals(WastageType.lot)) {
            service.deleteLotWastage(id, entityId);
        }
        if(wastageType.equals(WastageType.batch)) {
            service.deleteBatchWastage(id, entityId);
        }
        return new ResponseEntity<>("Wastage successfully deleted!", HttpStatus.OK);

    }

}
