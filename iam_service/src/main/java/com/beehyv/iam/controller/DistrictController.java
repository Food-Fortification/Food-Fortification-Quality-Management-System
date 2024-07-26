package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.DistrictRequestDto;
import com.beehyv.iam.service.DistrictService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/district")
@Tag(name = "District Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class DistrictController {
    private final DistrictService districtService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(districtService.getById(id));
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer pageNumber
            ,@RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(districtService.getAll(pageNumber,pageSize));
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DistrictRequestDto dto){
        districtService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody DistrictRequestDto dto){
        dto.setId(id);
        districtService.update(dto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        districtService.delete(id);
        return ResponseEntity.ok("Successfully Deleted");
    }

    @GetMapping("/{stateId}/districts")
    public ResponseEntity<?> getAllByStateId(@PathVariable Long stateId,
                                             @RequestParam(required = false) String search,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize){
        return new ResponseEntity<>(districtService.getAllDistrictsByStateId(stateId, search, pageNumber, pageSize),HttpStatus.OK);
    }

    @GetMapping("/stateGeoId/{stateGeoId}")
    public ResponseEntity<?> getAllByStateId(@PathVariable String stateGeoId){
        return new ResponseEntity<>(districtService.getAllDistrictsByStateGeoId(stateGeoId),HttpStatus.OK);
    }

    @PostMapping("/geoIds")
    public ResponseEntity<?> getAllByGeoIds(@RequestBody List<String> geoIds){
        return new ResponseEntity<>(districtService.getAllByGeoIds(geoIds),HttpStatus.OK);
    }
}
