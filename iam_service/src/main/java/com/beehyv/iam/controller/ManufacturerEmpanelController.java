package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.ManufacturerEmpanelRequestDto;
import com.beehyv.iam.enums.GeoType;
import com.beehyv.iam.service.ManufacturerEmpanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/empanel")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ManufacturerEmpanelController {
    private final ManufacturerEmpanelService manufacturerEmpanelService;

    @PostMapping
    public ResponseEntity<?> createManufacturerEmpanel(@Valid @RequestBody ManufacturerEmpanelRequestDto dto){
        return new ResponseEntity<>(manufacturerEmpanelService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(manufacturerEmpanelService.getManufacturerEmpanelByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManufacturerEmapnel(@PathVariable("id") Long id,
                                                @Valid @RequestBody ManufacturerEmpanelRequestDto dto){
        dto.setId(id);
        return new ResponseEntity<>(manufacturerEmpanelService.update(dto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturerEmapnelById(@PathVariable("id") Long id){
        manufacturerEmpanelService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/manufacturers")
    public ResponseEntity<?> getAllEmpanelledManufacturers(@RequestBody ManufacturerEmpanelRequestDto dto){
        return ResponseEntity.ok(manufacturerEmpanelService.getAllEmpanelledManufacturers(dto));
    }

    @GetMapping("categories/count")
    public ResponseEntity<?> manufacturerCategoriesCountByGeoId(@RequestParam GeoType type,
                                                                @RequestParam String geoId){
        return ResponseEntity.ok(manufacturerEmpanelService.getCategoriesCounts(type, geoId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> manufacturerCountByGeoId(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam GeoType type,
                                                      @RequestParam String geoId){
        return ResponseEntity.ok(manufacturerEmpanelService.getCategoryCounts(categoryId, type, geoId));
    }
}
