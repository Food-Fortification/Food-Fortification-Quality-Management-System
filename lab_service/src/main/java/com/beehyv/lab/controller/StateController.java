package com.beehyv.lab.controller;

import com.beehyv.lab.dto.requestDto.StateRequestDTO;
import com.beehyv.lab.service.StateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/state")
@Tag(name = "State Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class StateController {

    private final StateService stateService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(stateService.getById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody StateRequestDTO dto){
        stateService.create(dto);
        return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody StateRequestDTO dto){
        dto.setId(id);
        stateService.update(dto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        stateService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<?> getAllStates(@RequestParam(required = false) Integer pageNumber
            ,@RequestParam(required = false) Integer pageSize){
        return new ResponseEntity<>(stateService.findAll(pageNumber,pageSize),HttpStatus.OK);
    }

    @GetMapping("/country/{countryId}/states")
    public ResponseEntity<?> getAllStatesByCountryId(@PathVariable Long countryId,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(stateService.getAllStatesByCountryId(countryId, search, pageNumber,pageSize));
    }
}
