package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.AddressRequestDto;
import com.beehyv.iam.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Address Controller")
@RequestMapping("address")
@CrossOrigin(origins = {"*"})
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(addressService.getById(id));
    }
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer pageNumber
            ,@RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(addressService.findAll(pageNumber,pageSize));
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AddressRequestDto dto){
        addressService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody AddressRequestDto dto){
        dto.setId(id);
        addressService.update(dto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.ok("Successfully Deleted");
    }
    @GetMapping("/manufacturer/{manufacturerId}")
    public ResponseEntity<?> getByManufacturerId(@PathVariable Long manufacturerId){
        return new ResponseEntity<>(addressService.getByManufacturerId(manufacturerId),HttpStatus.OK);
    }

}
