package org.path.iam.controller;

import org.path.iam.dto.requestDto.ManufacturerDocsRequestDto;
import org.path.iam.service.ManufacturerDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/manufacturer-document")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class ManufacturerDocController {

    private final ManufacturerDocService manufacturerDocService;

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Valid @RequestBody ManufacturerDocsRequestDto manufacturerDocsRequestDto){
        manufacturerDocService.create(manufacturerDocsRequestDto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllManufacturers(@RequestParam(required = false) Integer pageNumber,
                                                 @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(manufacturerDocService.getALlManufacturerDocs(pageNumber,pageSize));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long manufacturerDocId){
        return ResponseEntity.ok( manufacturerDocService.getById(manufacturerDocId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateManufacturerDoc(@PathVariable("id")Long manufacturerDocId,
                                                   @Valid @RequestBody ManufacturerDocsRequestDto manufacturerDocsRequestDto){
        manufacturerDocsRequestDto.setId(manufacturerDocId);
        manufacturerDocService.updateManufacturerDoc(manufacturerDocsRequestDto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturerDocById(@PathVariable("id") Long manufacturerDocId){
        manufacturerDocService.deleteManufacturer(manufacturerDocId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
