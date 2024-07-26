package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.LocationRequestDto;
import com.beehyv.iam.service.CountryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Country Controller")
@RequestMapping("country")
@CrossOrigin(origins = {"*"})
public class CountryController {
    private final CountryService countryService;
    @GetMapping
    public ResponseEntity<?> getAllCountries(@RequestParam(required = false) String search,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(countryService.getAllCountries(search, pageNumber,pageSize));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(countryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LocationRequestDto dto){
        countryService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody LocationRequestDto dto){
        dto.setId(id);
        countryService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        countryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
