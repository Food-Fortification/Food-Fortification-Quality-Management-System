package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.DashboardRequestDto;
import com.beehyv.lab.dto.requestDto.LabRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.service.LabService;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Lab Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("")
public class LabController {
    @Autowired
    LabService labService;

    @PostMapping("/getAll")
    public ResponseEntity<ListResponse<LabResponseDTO>> getAllLabs(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize,
        @RequestParam(required = false) String search, @RequestBody(required = false) SearchListRequest searchListRequest) {
        ListResponse<LabResponseDTO> dtoList = labService.getAllLabs(searchListRequest, search, pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("{labId}")
    public ResponseEntity<LabResponseDTO> getLabById(@PathVariable("labId") Long labId) {
        LabResponseDTO dto = labService.getLabById(labId);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Long> addLab(@RequestBody LabRequestDTO labRequestDTO) {
        return new ResponseEntity<>(labService.createLab(labRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("{labId}")
    public ResponseEntity<String> updateLabById(@PathVariable("labId") Long labId, @RequestBody LabRequestDTO labRequestDTO) {
        labService.updateLabById(labId, labRequestDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{labId}")
    public ResponseEntity<String> deleteLabById(@PathVariable("labId") Long labId){
        labService.deleteLabById(labId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/byAddress")
    public ResponseEntity<?> getNearestLab(@RequestParam String address) {
        LabResponseDTO lab = labService.getNearestLab(address, null, null);
        if(lab != null) {
            return new ResponseEntity<>(lab, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/roleCategories/{labId}")
    public ResponseEntity<?> getAllRoleCategoriesForLab(@PathVariable("labId") Long labId) {
        Map<String, CategoryRoleResponseDto> allRoleCategoriesForLab = labService.getAllRoleCategoriesForLab(
            labId);
        return new ResponseEntity<>(allRoleCategoriesForLab, HttpStatus.OK);
    }

    @GetMapping("/manufacturer/{manufacturerId}/category/{categoryId}/active-labs")
    public ResponseEntity<ListResponse<LabListResponseDTO>> getAllActiveLabs(
        @PathVariable Long manufacturerId,
        @PathVariable Long categoryId,
        @RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize,
        @RequestParam(required = false) String search) {
        ListResponse<LabListResponseDTO> dtoList = labService.getAllActiveLabsForCategory(search, manufacturerId, categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PostMapping("labs/aggregate")
    public ResponseEntity<ListResponse<LabDashboardResponseDto>> getLabsAggregate(
                                                                                  @RequestBody DashboardRequestDto dto
                                                                                  ){
        ListResponse<LabDashboardResponseDto> dtoList = labService.getLabsAggregate(dto);
        return new ResponseEntity<>(dtoList,HttpStatus.OK);
    }

    @PostMapping("labSamples/{labId}/{type}")
    public ResponseEntity<ListResponse<LabSampleDetailsResponseDto>> getLabSamplesDetails(@PathVariable Long labId,
                                                                        @PathVariable String type,
                                                                        @RequestBody DashboardRequestDto dto){
        ListResponse<LabSampleDetailsResponseDto> dtoList = labService.getLabSamplesDetails(dto,labId,type);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
