package com.beehyv.iam.controller;
import com.beehyv.iam.dto.requestDto.ManufacturerAttributeScoreRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerAttributeScoreResponseDto;
import com.beehyv.iam.service.ManufacturerAttributeScoreService;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Manufacturer Attribute Score Controller")
@RequestMapping(path = "/manufacturer-attributes-score")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ManufacturerAttributeScoreController {
    private final ManufacturerAttributeScoreService manufacturerAttributeScoreService;
    @PostMapping
    public ResponseEntity<Long> createManufacturerAttributeScore( @Valid @RequestBody ManufacturerAttributeScoreRequestDto dto){
        return new ResponseEntity<>(manufacturerAttributeScoreService.create(dto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ManufacturerAttributeScoreRequestDto dto){
        dto.setId(id);
        manufacturerAttributeScoreService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        manufacturerAttributeScoreService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerAttributeScoreResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(manufacturerAttributeScoreService.getById(id));
    }
}
