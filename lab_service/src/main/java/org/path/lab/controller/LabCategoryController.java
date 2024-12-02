package org.path.lab.controller;

import org.path.lab.dto.requestDto.LabCategoryRequestDto;
import org.path.lab.service.LabCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Lab Category Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("/lab/category")
public class LabCategoryController {

    @Autowired
    LabCategoryService labCategoryService;


    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(labCategoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LabCategoryRequestDto dto){
        labCategoryService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody LabCategoryRequestDto dto){
        dto.setId(id);
        labCategoryService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        labCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byLabId/{labId}")
    public ResponseEntity<?> getByLabId(@PathVariable Long labId){
        return ResponseEntity.ok(labCategoryService.getByLabId(labId));
    }

    @GetMapping("/labId/{labId}")
    public ResponseEntity<?> getCategoryIdsByLabId(@PathVariable Long labId){
        return ResponseEntity.ok(labCategoryService.getCategoryIdsByLabId(labId));
    }

}
