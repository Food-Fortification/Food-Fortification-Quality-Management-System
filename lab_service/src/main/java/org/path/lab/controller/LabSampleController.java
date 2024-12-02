package org.path.lab.controller;

import org.path.lab.dto.requestDto.LabSampleRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabNameAddressResponseDto;
import org.path.lab.dto.responseDto.LabSampleResponseDto;
import org.path.lab.dto.responseDto.LabSampleResultResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.enums.SampleType;
import org.path.lab.helper.RestHelper;
import org.path.lab.service.LabSampleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Lab Sample Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("{labId}/sample")
public class LabSampleController {

    @Autowired
    LabSampleService labSampleService;
    @Autowired
    RestHelper restHelper;
    @PostMapping("list")
    public ResponseEntity<ListResponse<LabSampleResponseDto>> getAllLabSamples(
            @RequestBody(required = false) SearchListRequest searchListRequest,
            @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        if(pageNumber!=null&&pageNumber>0)pageNumber=pageNumber-1;
        ListResponse<LabSampleResponseDto> dtoList = labSampleService.getAllLabSamples(pageNumber, pageSize, searchListRequest);
        return new ResponseEntity<>(dtoList, HttpStatus.CREATED);
    }

    @GetMapping("{sampleId}")
    public ResponseEntity<LabSampleResponseDto> getLabSampleById(@PathVariable("sampleId") Long sampleId) {
        LabSampleResponseDto dto = labSampleService.getLabSampleById(sampleId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("batch/{batchId}")
    public ResponseEntity<ListResponse<LabSampleResponseDto>> getAllLabSamplesByBatchId(
            @PathVariable Long batchId) {
        ListResponse<LabSampleResponseDto> dtoList = labSampleService.getAllLabSamplesByBatchId(batchId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("lot/{lotId}")
    public ResponseEntity<ListResponse<LabSampleResponseDto>> getAllLabSamplesByLotId(
            @PathVariable Long lotId) {
        ListResponse<LabSampleResponseDto> dtoList = labSampleService.getAllLabSamplesByLotId(lotId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/{sampleType}")
    public ResponseEntity<?> getAllLabSamplesByLotIds(
            @PathVariable SampleType sampleType,
            @RequestBody List<Long> lotIds) {
        return new ResponseEntity<>(labSampleService.getAllLabSamplesByLotIds(sampleType, lotIds), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLabSample(@Valid @RequestBody LabSampleRequestDTO dto, @RequestParam(required = false) Boolean self) {
        return new ResponseEntity<>(labSampleService.createLabSample(dto,self), HttpStatus.CREATED);
    }

    @PutMapping("{sampleId}")
    public ResponseEntity<LabSampleResultResponseDto> updateLabSampleById(@PathVariable("labId") Long labId, @PathVariable("sampleId") Long sampleId,
                                                      @RequestBody @Valid LabSampleRequestDTO dto) {
        dto.setId(sampleId);
        LabSampleResultResponseDto labSampleResult =labSampleService.updateLabSample(dto);
        return new ResponseEntity<>(labSampleResult, HttpStatus.OK);
    }

    @DeleteMapping("{sampleId}")
    public ResponseEntity<String> deleteLabSampleById(@PathVariable("sampleId") Long sampleId) {
        labSampleService.deleteLabSample(sampleId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("{sampleId}/state")
    public ResponseEntity<String> updateSampleStatus(@PathVariable Long labId, @PathVariable Long sampleId, @RequestParam String state, @RequestParam(required = false,name="date") String dateOfReceiving, @RequestParam Long categoryId){
        labSampleService.updateSampleStatus(categoryId, sampleId, state, dateOfReceiving);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    @GetMapping("/{entityType}/address/{entityId}")
    public ResponseEntity<LabNameAddressResponseDto> getLabNameAddressByEntityId(@PathVariable Long labId, @PathVariable SampleType entityType, @PathVariable Long entityId){
        return new ResponseEntity<>(labSampleService.getLabNameAddressByEntityId(entityType, entityId), HttpStatus.OK);
    }

    @GetMapping("event/batch/{batchId}")
    public ResponseEntity<List<LabSampleResponseDto>> getAllLabSamplesByBatchIdForEventUpdate(
            @PathVariable Long batchId, @PathVariable Long labId) {
        List<LabSampleResponseDto> dtoList = labSampleService.getAllLabSamplesByBatchIdForEventUpdate(batchId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("event/lot/{lotId}")
    public ResponseEntity<List<LabSampleResponseDto>> getAllLabSamplesByLotIdForEventUpdate(
            @PathVariable Long lotId, @PathVariable Long labId) {
        List<LabSampleResponseDto> dtoList = labSampleService.getAllLabSamplesByLotIdForEventUpdate(lotId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}
