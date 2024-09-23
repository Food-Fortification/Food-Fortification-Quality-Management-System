package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabTestTypeRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.LabTestTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabTestType;
import com.beehyv.lab.service.LabTestTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Lab Test Type Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("test/type")
public class LabTestTypeController {
    @Autowired
    LabTestTypeService labTestTypeService;

    @PostMapping("/getAll")
    public ResponseEntity<ListResponse<LabTestTypeResponseDTO>> getAllLabTestTypes(
        @RequestBody(required = false) SearchListRequest searchListRequest,
        @RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize) {
        ListResponse<LabTestTypeResponseDTO> dtoList = labTestTypeService.getAllLabTestTypes(searchListRequest,pageNumber,pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.CREATED);
    }

    @GetMapping("{labTestTypeId}")
    public ResponseEntity<LabTestTypeResponseDTO> getLabTestTypeById(@PathVariable("labTestTypeId") Long labTestTypeId) {
        LabTestTypeResponseDTO dto = labTestTypeService.getLabTestTypeById(labTestTypeId);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{categoryId}/filterType")
    public ResponseEntity<?> getLabTestTypeByType(@RequestParam LabTestType.Type type,@PathVariable Long categoryId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize){
        List<LabTestTypeResponseDTO> dto = labTestTypeService.getLabTestTypesByType(type,categoryId,pageNumber,pageSize);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<String> addLabTestType(@RequestBody LabTestTypeRequestDTO labTestTypeRequestDTO) {
        labTestTypeService.addLabTestType(labTestTypeRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @PutMapping("{labTestTypeId}")
    public ResponseEntity<String> updateLabTestTypeById(@PathVariable("labTestTypeId") Long labTestTypeId, @RequestBody LabTestTypeRequestDTO labTestTypeRequestDTO) {
        labTestTypeService.updateLabTestTypeById(labTestTypeId, labTestTypeRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{labTestTypeId}")
    public ResponseEntity<String> deleteLabTestTypeById(@PathVariable("labTestTypeId") Long labTestTypeId){
        labTestTypeService.deleteLabTestTypeById(labTestTypeId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
