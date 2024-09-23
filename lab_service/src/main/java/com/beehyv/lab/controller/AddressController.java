package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.AddressRequestDTO;
import com.beehyv.lab.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Address Controller")
@RequestMapping("address")
@CrossOrigin(origins = {"*"})
public class AddressController {

    private final AddressService addressService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(addressService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AddressRequestDTO dto){
        addressService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody AddressRequestDTO dto){
        dto.setId(id);
        addressService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/lab-address")
    public ResponseEntity<?> getCompleteAddressForLab(@RequestParam List<Long> labIds){
        return ResponseEntity.ok(addressService.getCompleteAddressForLab(labIds));
    }
    @GetMapping("/{labId}/lab-address")
    public ResponseEntity<?> getNameAndCompleteAddressForLab(@PathVariable Long labId){
        return ResponseEntity.ok(addressService.getNameAndCompleteAddressForLab(labId));
    }

}
