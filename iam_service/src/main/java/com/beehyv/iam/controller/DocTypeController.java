package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.DocTypeRequestDto;
import com.beehyv.iam.service.DocTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/document-type")
@Tag(name = "Document Type Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
@Slf4j
public class DocTypeController {
    private final DocTypeService docTypeService;
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DocTypeRequestDto docTypeDto){
        docTypeService.create(docTypeDto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long docTypeId){
        return ResponseEntity.ok(docTypeService.getById(docTypeId));
    }
    @GetMapping
    public ResponseEntity<?> getAllDocType(@RequestParam(required = false) Integer pageNumber,@RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(docTypeService.getAllDocTpe(pageNumber,pageSize));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Long docTypeId,@Valid @RequestBody DocTypeRequestDto docTypeRequestDto){
        docTypeRequestDto.setId(docTypeId);
        docTypeService.update(docTypeRequestDto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long docTypeId){
        docTypeService.delete(docTypeId);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }

}
