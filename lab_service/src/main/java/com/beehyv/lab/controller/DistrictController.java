package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.DistrictRequestDTO;
import com.beehyv.lab.service.DistrictService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "District Controller")
@RequestMapping("district")
@CrossOrigin(origins = {"*"})
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(districtService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DistrictRequestDTO dto){
        districtService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody DistrictRequestDTO dto){
        dto.setId(id);
        districtService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        districtService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer pageNumber
        ,@RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(districtService.getAll(pageNumber,pageSize));
    }

    @GetMapping("/state/{stateId}/districts")
    public ResponseEntity<?> getAllByStateId(@PathVariable Long stateId,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize){
        return new ResponseEntity<>(districtService.getAllDistrictsByStateId(stateId, search, pageNumber, pageSize),HttpStatus.OK);
    }

}
