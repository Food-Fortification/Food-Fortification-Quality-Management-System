package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import com.beehyv.lab.dto.responseDto.LabTestReferenceMethodResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.LabTestReferenceMethodService;
import com.beehyv.lab.dto.responseDto.LabMethodResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Lab Test Reference Method Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("method")
public class LabTestReferenceMethodController {
    @Autowired
    LabTestReferenceMethodService labTestReferenceMethodService;

    @GetMapping("allMethods/{category_id}")
    public ResponseEntity<ListResponse<LabMethodResponseDto>> getAllMethods(@PathVariable("category_id") Long categoryId,
                                           @RequestParam Long labSampleId,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize) {
        ListResponse<LabMethodResponseDto> responseDtoList =
                labTestReferenceMethodService.getAllMethodsByCategoryId(categoryId, labSampleId,pageNumber, pageSize);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("{method_id}")
    public ResponseEntity<LabTestReferenceMethodResponseDTO> getMethodById(@PathVariable("method_id") Long methodId) {
        LabTestReferenceMethodResponseDTO dto = labTestReferenceMethodService.getLabTestReferenceMethodById(methodId);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addMethod(@RequestBody LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO) {
        labTestReferenceMethodService.addLabTestReferenceMethod(labTestReferenceMethodRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @PutMapping("{method_id}")
    public ResponseEntity<String> updateMethodById(@PathVariable("method_id") Long methodId, @RequestBody LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO) {
        labTestReferenceMethodService.updateLabTestReferenceMethodById(methodId, labTestReferenceMethodRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{method_id}")
    public ResponseEntity<String> deleteMethodById(@PathVariable("method_id") Long methodId){
        labTestReferenceMethodService.deleteLabTestReferenceMethodById(methodId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
