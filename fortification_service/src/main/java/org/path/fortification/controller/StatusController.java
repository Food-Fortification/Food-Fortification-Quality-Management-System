package org.path.fortification.controller;

import org.path.fortification.dto.requestDto.StatusRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StatusResponseDto;
import org.path.fortification.service.StatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "Status Controller")
@RequestMapping("/status")
public class StatusController {

    private StatusService service;

    @PostMapping
    public ResponseEntity<?> createStatus(@Valid @RequestBody StatusRequestDto dto) {
        service.createStatus(dto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<StatusResponseDto> getStatusById(@PathVariable("id") Long id) {
        StatusResponseDto dto = service.getStatusById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListResponse<StatusResponseDto>> getAllStatuses(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<StatusResponseDto> statuses = service.getAllStatuses(pageNumber, pageSize);
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id,
                                                         @RequestBody @Valid StatusRequestDto dto) {
        dto.setId(id);
        service.updateStatus(dto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable("id") Long id) {
        service.deleteStatus(id);
        return new ResponseEntity<>("Status successfully deleted!", HttpStatus.OK);
    }

}
