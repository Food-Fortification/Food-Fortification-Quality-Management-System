package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.DocTypeRequestDTO;
import com.beehyv.lab.dto.responseDto.DocTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.DocTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Doc Type Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("document/type")
public class DocTypeController {
    @Autowired
    DocTypeService docTypeService;

    @GetMapping
    public ResponseEntity<ListResponse<DocTypeResponseDTO>> getAllDocTypes(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<DocTypeResponseDTO> dtoList = docTypeService.getAllDocTypes(pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("{docTypeId}")
    public ResponseEntity<DocTypeResponseDTO> getDocTypeById(@PathVariable("docTypeId") Long docTypeId) {
        DocTypeResponseDTO dto = docTypeService.getDocTypeById(docTypeId);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addDocType(@RequestBody DocTypeRequestDTO docTypeRequestDTO) {
        docTypeService.addDocType(docTypeRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @PutMapping("{docTypeId}")
    public ResponseEntity<String> updateDocTypeById(@PathVariable("docTypeId") Long docTypeId, @RequestBody DocTypeRequestDTO docTypeRequestDTO) {
        docTypeService.updateDocTypeById(docTypeId, docTypeRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{docTypeId}")
    public ResponseEntity<String> deleteDocTypeById(@PathVariable("docTypeId") Long docTypeId){
        docTypeService.deleteDocTypeById(docTypeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
