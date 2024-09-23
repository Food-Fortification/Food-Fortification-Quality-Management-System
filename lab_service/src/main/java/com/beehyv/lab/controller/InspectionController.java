package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.InspectionRequestDTO;
import com.beehyv.lab.enums.SampleType;
import com.beehyv.lab.service.InspectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Inspection Controller")
@RequestMapping("inspection")
@CrossOrigin(origins = {"*"})
public class InspectionController {

  private final InspectionService inspectionService;

  @GetMapping("{id}")
  public ResponseEntity<?> getById(@PathVariable Long id){
    return ResponseEntity.ok(inspectionService.getById(id));
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody InspectionRequestDTO dto){
    return new ResponseEntity<>(inspectionService.create(dto), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody InspectionRequestDTO dto){
    dto.setId(id);
    return new ResponseEntity<>(inspectionService.update(dto),HttpStatus.OK);
  }
  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    inspectionService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("samples/{type}/category/{categoryId}")
  public ResponseEntity<?> getAllIdsBySampleType(@PathVariable SampleType type,
                                                 @RequestParam(required = false) String sampleState,
                                                 @PathVariable Long categoryId,
                                                 @RequestParam(required = false) Integer pageNumber,
                                                 @RequestParam(required = false) Integer pageSize){
    return ResponseEntity.ok(inspectionService.getAllIdsBySampleType(type,categoryId,sampleState,pageNumber,pageSize));
  }

  @GetMapping("/sample/status")
  public ResponseEntity<?> getInspectionSampleStatus(@RequestParam SampleType sampleType, @RequestParam Long id){
    return new ResponseEntity<>(inspectionService.getInspectionSampleStatus(sampleType,id),HttpStatus.OK);
  }


}
