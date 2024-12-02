package org.path.iam.controller;

import org.path.iam.dto.requestDto.AttributeRequestDto;
import org.path.iam.dto.responseDto.AttributeResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.service.AttributeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Attribute Controller")
@RequestMapping("attribute")
@CrossOrigin(origins = {"*"})
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping("/{id}")
    public ResponseEntity<AttributeResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(attributeService.getById(id));
    }
    @GetMapping
    public ResponseEntity<ListResponse<AttributeResponseDto>> getAll(@RequestParam(required = false) Integer pageNumber
            , @RequestParam(required = false) Integer pageSize){
        return ResponseEntity.ok(attributeService.findAll(pageNumber,pageSize));
    }
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody AttributeRequestDto dto){

        return new ResponseEntity<>(attributeService.create(dto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody AttributeRequestDto dto){
        dto.setId(id);
        attributeService.update(dto);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        attributeService.delete(id);
        return ResponseEntity.ok("Successfully Deleted");
    }
}
