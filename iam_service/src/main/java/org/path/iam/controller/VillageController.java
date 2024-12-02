package org.path.iam.controller;

import org.path.iam.dto.requestDto.VillageRequestDto;
import org.path.iam.service.VillageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/village")
@Tag(name = "Village Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class VillageController {
    private final VillageService villageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(villageService.getById(id));
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer pageNumber
            ,@RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(villageService.findAll(pageNumber,pageSize));
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VillageRequestDto dto){
        return new ResponseEntity<>(villageService.create(dto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody VillageRequestDto dto){
        dto.setId(id);
        villageService.update(dto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        villageService.delete(id);
        return ResponseEntity.ok("Successfully Deleted");
    }

    @GetMapping("/{districtId}/villages")
    public ResponseEntity<?> getAllVillages(@PathVariable Long districtId, @RequestParam(required = false) Integer pageNumber
            , @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(villageService.getAllVillagesByDistrictId(districtId,pageNumber,pageSize));
    }
}
