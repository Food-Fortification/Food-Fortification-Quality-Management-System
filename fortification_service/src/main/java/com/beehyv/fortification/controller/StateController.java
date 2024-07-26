package com.beehyv.fortification.controller;

import com.beehyv.fortification.dto.requestDto.StateRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.service.StateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "State Controller")
@RequestMapping("/state")
public class StateController {

    private StateService service;

    @PostMapping
    public ResponseEntity<?> createState(@Valid @RequestBody StateRequestDto dto) {
        service.createState(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<StateResponseDto> getStateById(@PathVariable("id") Long id) {
        StateResponseDto dto = service.getStateById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<Long> getStateIdByName(@PathVariable("name") String name){
        Long id = service.getStateIdByName(name);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<StateResponseDto>> getAllStates(@RequestParam(required = false) Integer pageNumber,
                                                                       @RequestParam(required = false) Integer pageSize,
                                                                       @RequestParam(required = false) StateType type) {
        ListResponse<StateResponseDto> states = service.getAllStates(type,pageNumber, pageSize);
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateState(@PathVariable("id") Long id,
                                                       @RequestBody @Valid StateRequestDto dto) {
        dto.setId(id);
        service.updateState(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteState(@PathVariable("id") Long id) {
        service.deleteState(id);
        return new ResponseEntity<>("State successfully deleted!", HttpStatus.OK);
    }

}
