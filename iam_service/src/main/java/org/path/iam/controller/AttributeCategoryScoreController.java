package org.path.iam.controller;
import org.path.iam.dto.requestDto.AttributeCategoryScoreRequestDto;
import org.path.iam.dto.responseDto.AttributeCategoryScoreResponseDto;
import org.path.iam.service.AttributeCategoryScoreService;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Attribute Category Score  Controller")
@RequestMapping(path = "/attribute-category-score")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class AttributeCategoryScoreController {

    private final AttributeCategoryScoreService attributeCategoryScoreService;
    @PostMapping
    public ResponseEntity<Long> createAttributeCategoryScore(@Valid @RequestBody AttributeCategoryScoreRequestDto dto){
        return new ResponseEntity<>(attributeCategoryScoreService.create(dto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AttributeCategoryScoreRequestDto dto){
        dto.setId(id);
        attributeCategoryScoreService.update(dto);
        return new ResponseEntity<>("successfully updated",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        attributeCategoryScoreService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeCategoryScoreResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(attributeCategoryScoreService.getById(id));
    }
}
