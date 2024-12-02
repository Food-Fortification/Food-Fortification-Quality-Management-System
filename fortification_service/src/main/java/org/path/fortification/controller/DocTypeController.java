package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.DocTypeRequestDto;
import org.path.fortification.dto.responseDto.DocTypeResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.service.DocTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Doc Type Controller")
@RequestMapping("/docType")
public class DocTypeController {

    private DocTypeService service;

    @PostMapping
    public ResponseEntity<?> createSDocType(@Valid @RequestBody DocTypeRequestDto dto) {
        service.createDocType(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<DocTypeResponseDto> getDocTypeById(@PathVariable("id") Long id) {
        DocTypeResponseDto dto = service.getDocTypeById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<DocTypeResponseDto>> getAllDocTypes(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<DocTypeResponseDto> docs = service.getAllDocTypes(pageNumber, pageSize);
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateDocType(@PathVariable("id") Long id,
                                         @RequestBody @Valid DocTypeRequestDto dto) {
        dto.setId(id);
        service.updateDocType(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDocType(@PathVariable("id") Long id) {
        service.deleteDocType(id);
        return new ResponseEntity<>("State successfully deleted!", HttpStatus.OK);
    }
}
