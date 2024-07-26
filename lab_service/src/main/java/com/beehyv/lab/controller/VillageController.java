package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.VillageRequestDTO;
import com.beehyv.lab.service.VillageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Village Controller")
@RequestMapping("village")
@CrossOrigin(origins = {"*"})
public class VillageController {

    private final VillageService villageService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(villageService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VillageRequestDTO dto){
        return new ResponseEntity<>(villageService.create(dto), HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody VillageRequestDTO dto){
        dto.setId(id);
        villageService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        villageService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer pageNumber
        ,@RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(villageService.findAll(pageNumber,pageSize));
    }

    @GetMapping("/district/{districtId}/villages")
    public ResponseEntity<?> getAllVillages(@PathVariable Long districtId, @RequestParam(required = false) Integer pageNumber
        , @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(villageService.getAllVillagesByDistrictId(districtId,pageNumber,pageSize));
    }

}
