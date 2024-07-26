package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.CountryRequestDTO;
import com.beehyv.lab.service.CountryService;
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

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(countryService.getCountryById(id));
    }
    @GetMapping
    public ResponseEntity<?> getAllCountries(@RequestParam(required = false) String search,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(countryService.getAllCountries(search, pageNumber,pageSize));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CountryRequestDTO dto){
        countryService.createCountry(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody CountryRequestDTO dto){
        dto.setId(id);
        countryService.updateCountry(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        countryService.deleteCountry(id);
        return ResponseEntity.ok().build();
    }
}

