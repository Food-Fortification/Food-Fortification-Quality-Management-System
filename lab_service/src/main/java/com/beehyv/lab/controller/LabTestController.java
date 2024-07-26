package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.LabTestRequestDTO;
import com.beehyv.lab.dto.responseDto.LabTestDetailsResponseDto;
import com.beehyv.lab.dto.responseDto.LabTestResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.service.LabTestService;
import com.beehyv.lab.dto.requestDto.CreateSampleRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Lab Test Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("test")
public class LabTestController {
    @Autowired
    LabTestService labTestService;

    @GetMapping
    public ResponseEntity<ListResponse<LabTestResponseDTO>> getAllLabTests(@RequestParam(required = false) Integer pageNumber,
                                                                           @RequestParam(required = false) Integer pageSize) {
        ListResponse<LabTestResponseDTO> dtoList = labTestService.getAllLabTests(pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("{testId}")
    public ResponseEntity<LabTestResponseDTO> getLabTestById(@PathVariable("testId") Long sampleId) {
        LabTestResponseDTO dto = labTestService.getLabTestById(sampleId);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{testId}")
    public ResponseEntity<String> updateLabTestById(@PathVariable("testId") Long sampleId, @RequestBody LabTestRequestDTO labTestRequestDTO) {
        labTestService.updateLabTestById(sampleId, labTestRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{testId}")
    public ResponseEntity<String> deleteLabTestById(@PathVariable("testId") Long sampleId){
        labTestService.deleteLabTestById(sampleId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("{batchId}/labDetails")
    public ResponseEntity<ListResponse<LabTestResponseDTO>> getLabTestDetails(@PathVariable Long batchId,@RequestParam(required = false) Integer pageNumber,
                                                                              @RequestParam(required = false) Integer pageSize){
        ListResponse<LabTestResponseDTO> dtoList = labTestService.getDetailsByBatchId(batchId,pageNumber,pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}
