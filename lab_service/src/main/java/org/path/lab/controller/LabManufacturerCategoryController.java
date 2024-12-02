package org.path.lab.controller;

import org.path.lab.dto.requestDto.LabManufacturerRequestDTO;
import org.path.lab.service.LabManufacturerCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Lab Manufacturer Category Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("/manufacturer")
public class LabManufacturerCategoryController {
    @Autowired
    LabManufacturerCategoryService labManufacturerCategoryService;

    @PostMapping
    public ResponseEntity<String> addLabManufacturerCategory(@RequestBody LabManufacturerRequestDTO labManufacturerRequestDTO) {
        labManufacturerCategoryService.create(labManufacturerRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }


    @DeleteMapping("{labManufacturerCategorylabId}")
    public ResponseEntity<String> deleteLabManufacturerCategoryById(@PathVariable("labManufacturerCategorylabId") Long id){
        labManufacturerCategoryService.delete(id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping(path = "/manufacturer-lab-list")
    public ResponseEntity<?> getLabListByManufacturerId(
            @RequestParam(required = true) Long manufacturerId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize){
        return new ResponseEntity<>(labManufacturerCategoryService.getLabsByManufacturerId(search,manufacturerId, pageNumber, pageSize), HttpStatus.OK);
    }
}
