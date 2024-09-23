package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.ManufacturerCategoryAttributesRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.ManufacturerCategoryAttributesResponseDto;
import com.beehyv.iam.service.ManufacturerCategoryAttributesService;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Manufacturer Attribute Category Controller")
@RequestMapping(path = "/manufacturer-attributes")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ManufacturerCategoryAttributesController {

  private final ManufacturerCategoryAttributesService manufacturerAttributesService;

  @PostMapping
  public ResponseEntity<Long> createManufacturer( @RequestBody ManufacturerCategoryAttributesRequestDto dto){
    return new ResponseEntity<>(manufacturerAttributesService.create(dto), HttpStatus.CREATED);
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ManufacturerCategoryAttributesRequestDto dto){
    dto.setId(id);
    manufacturerAttributesService.update(dto);
    return new ResponseEntity<>("successfully updated",HttpStatus.OK);
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    manufacturerAttributesService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ManufacturerCategoryAttributesResponseDto> getById(@PathVariable Long id){
    return ResponseEntity.ok(manufacturerAttributesService.getById(id));
  }
  @GetMapping
  public ResponseEntity<ListResponse<ManufacturerCategoryAttributesResponseDto>> getAll(@RequestParam(required = false) Integer pageNumber
          , @RequestParam(required = false) Integer pageSize){
    return ResponseEntity.ok(manufacturerAttributesService.findAll(pageNumber,pageSize));
  }

  @GetMapping("/manufacturer/{manufacturerId}")
  public ResponseEntity<List<ManufacturerCategoryAttributesResponseDto>> getAllManufacturerAttributesForManufacturer(@PathVariable Long manufacturerId,
                                                                                                                     @RequestParam(required = false) Long userId){
    return ResponseEntity.ok(manufacturerAttributesService.getAllManufacturerAttributesForManufacturer(manufacturerId, userId));
  }

}
