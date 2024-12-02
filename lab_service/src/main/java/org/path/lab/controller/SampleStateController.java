package org.path.lab.controller;

import org.path.lab.dto.requestDto.SampleStateRequestDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleStateResponseDTO;
import org.path.lab.service.SampleStateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Sample State Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("sample-state")
@RequiredArgsConstructor
public class SampleStateController {

    private final SampleStateService sampleStateService;

    @GetMapping
    public ResponseEntity<ListResponse<SampleStateResponseDTO>> getAllSampleStates(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<SampleStateResponseDTO> dtoList = sampleStateService.getAll(pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SampleStateResponseDTO> getById(@PathVariable("id") Long id) {
        SampleStateResponseDTO dto = sampleStateService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SampleStateRequestDto sampleStateRequestDto) {
        sampleStateService.create(sampleStateRequestDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateDocTypeById(@PathVariable("id") Long id, @RequestBody SampleStateRequestDto sampleStateRequestDto) {
        sampleStateRequestDto.setId(id);
        sampleStateService.update(sampleStateRequestDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDocTypeById(@PathVariable("id") Long id){
        sampleStateService.delete(id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
