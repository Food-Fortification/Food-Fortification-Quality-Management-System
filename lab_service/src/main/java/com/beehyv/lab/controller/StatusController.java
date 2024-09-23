package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.StatusRequestDTO;
import com.beehyv.lab.service.StatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
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
@RequestMapping("/status")
@Tag(name = "Status Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class StatusController {
  private final StatusService statusService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id){
    return ResponseEntity.ok(statusService.getById(id));
  }
  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody StatusRequestDTO dto){
    return new ResponseEntity<>(statusService.create(dto), HttpStatus.CREATED);
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody StatusRequestDTO dto){
    dto.setId(id);
    statusService.update(dto);
    return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    statusService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<?> getAllStatus(@RequestParam(required = false) Integer pageNumber
      ,@RequestParam(required = false) Integer pageSize){
    return new ResponseEntity<>(statusService.findAll(pageNumber,pageSize),HttpStatus.OK);
  }

}
