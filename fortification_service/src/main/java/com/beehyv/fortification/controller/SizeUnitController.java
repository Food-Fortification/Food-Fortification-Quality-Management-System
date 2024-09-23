package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.SizeUnitResponseDto;
import com.beehyv.fortification.service.SizeUnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Size Unit Controller")
@RequestMapping("/{categoryId}/batch/{batchId}/size-unit")
public class  SizeUnitController {

    private SizeUnitService service;

    @PostMapping
    public ResponseEntity<?> createSizeUnit(@Valid @RequestBody SizeUnitRequestDto dto, @PathVariable String categoryId) {
        service.createSizeUnit(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<SizeUnitResponseDto> getSizeUnitById(@PathVariable("id") Long id, @PathVariable String batchId, @PathVariable String categoryId) {
        SizeUnitResponseDto dto = service.getSizeUnitById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<SizeUnitResponseDto>> getAllSizeUnitsForBatch(@PathVariable Long batchId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize, @PathVariable String categoryId) {
        ListResponse<SizeUnitResponseDto> sizeUnits = service.getAllSizeUnits(batchId, pageNumber, pageSize);
        return new ResponseEntity<>(sizeUnits, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateSizeUnit(@PathVariable("id") Long id,
                                            @RequestBody @Valid SizeUnitRequestDto dto, @PathVariable String categoryId) {
        dto.setId(id);
        service.updateSizeUnit(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSizeUnit(@PathVariable("id") Long id, @PathVariable String batchId) {
        service.deleteSizeUnit(id);
        return new ResponseEntity<>("SizeUnit successfully deleted!", HttpStatus.OK);
    }
    @PostMapping("/add/list")
    public ResponseEntity<?> addMultipleSizeUnits(@RequestBody List<SizeUnitRequestDto> sizeUnits, @PathVariable Long batchId, @PathVariable Long categoryId){
        return new ResponseEntity<>(service.createSizeUnits(sizeUnits,batchId),HttpStatus.CREATED);
    }
    @PutMapping("/update/list")
    public ResponseEntity<?> updateMultipleSizeUnits(@RequestBody List<SizeUnitRequestDto> sizeUnits, @PathVariable Long batchId, @PathVariable Long categoryId){
        return new ResponseEntity<>(service.updateSizeUnits(sizeUnits,batchId),HttpStatus.OK);
    }
}
