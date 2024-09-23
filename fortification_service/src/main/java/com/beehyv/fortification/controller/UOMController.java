package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.UOMResponseDto;
import com.beehyv.fortification.service.UOMService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Unit Of Measurement(UOM) Controller")
@RequestMapping("/uom")
public class UOMController {

    private UOMService service;

    @PostMapping
    public ResponseEntity<?> createUOM(@Valid @RequestBody UOMRequestDto dto) {
        service.createUOM(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UOMResponseDto> getUOMById(@PathVariable("id") Long id) {
        UOMResponseDto dto = service.getUOMById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<UOMResponseDto>> getAllUOMs(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<UOMResponseDto> uoms = service.getAllUOMs(pageNumber, pageSize);
        return new ResponseEntity<>(uoms, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUOM(@PathVariable("id") Long id,
                                                   @RequestBody @Valid UOMRequestDto dto) {
        dto.setId(id);
        service.updateUOM(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUOM(@PathVariable("id") Long id) {
        service.deleteUOM(id);
        return new ResponseEntity<>("UOM successfully deleted!", HttpStatus.OK);
    }

}
