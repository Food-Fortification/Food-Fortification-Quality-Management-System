package org.path.lab.controller;

import org.path.lab.dto.requestDto.CategoryDocumentRequirementRequestDTO;
import org.path.lab.dto.responseDto.LabTestReferenceMethodResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleRequirementsResponseDTO;
import org.path.lab.dto.responseDto.CategoryDocumentRequirementResponseDTO;
import org.path.lab.entity.LabTestType;
import org.path.lab.enums.CategoryDocRequirementType;
import org.path.lab.service.CategoryDocumentRequirementService;
import org.path.lab.service.LabTestReferenceMethodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Category Document Requirement Controller")
@CrossOrigin(origins = {"*"})
@RequestMapping("document")
public class CategoryDocumentRequirementController {

    @Autowired
    CategoryDocumentRequirementService categoryDocumentRequirementService;

    @Autowired
    LabTestReferenceMethodService labTestReferenceMethodService;

    @GetMapping
    public ResponseEntity<ListResponse<CategoryDocumentRequirementResponseDTO>> getAllCategoryDocumentRequirements(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        ListResponse<CategoryDocumentRequirementResponseDTO> dtoList = categoryDocumentRequirementService.getAllCategoryDocumentRequirements(pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("{categoryId}/sample/requirements")
    public ResponseEntity<SampleRequirementsResponseDTO> getAllCategoryDocumentRequirementsByCategoryId(@PathVariable Long categoryId, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize , @RequestParam(required = false) LabTestType.Type type, @RequestParam(required = false) Long manufacturerId) {
        List<CategoryDocumentRequirementResponseDTO> dtoListDoc = categoryDocumentRequirementService.getAllCategoryDocumentRequirementsByCategoryIdAndType(categoryId, CategoryDocRequirementType.TEST, pageNumber, pageSize);
        List<LabTestReferenceMethodResponseDTO> dtoListReference = labTestReferenceMethodService.getAllLabTestReferenceMethodsByCategoryId(categoryId,pageNumber, pageSize, type, manufacturerId);
        SampleRequirementsResponseDTO responseDTO = new SampleRequirementsResponseDTO();
        responseDTO.setCategoryId(categoryId);
        responseDTO.setCategoryDocumentRequirements(dtoListDoc);
        responseDTO.setLabTestReferenceMethods(dtoListReference);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("category/type/{type}")
    public ResponseEntity<ListResponse<CategoryDocumentRequirementResponseDTO>> getAllCategoryDocumentRequirementsForSample(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize , @PathVariable String type) {
        ListResponse<CategoryDocumentRequirementResponseDTO> dtoListDoc = categoryDocumentRequirementService.getAllCategoryDocumentRequirementsByType(CategoryDocRequirementType.valueOf(type.toUpperCase()), pageNumber, pageSize);
        return ResponseEntity.ok(dtoListDoc);
    }

    @GetMapping("{documentRequiredId}")
    public ResponseEntity<CategoryDocumentRequirementResponseDTO> getCategoryRequirementById(@PathVariable("documentRequiredId") Long documentRequiredId) {
        CategoryDocumentRequirementResponseDTO dto = categoryDocumentRequirementService.getCategoryDocumentRequirementById(documentRequiredId);
        if(dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> addCategoryDocumentRequirement(@RequestBody CategoryDocumentRequirementRequestDTO dto) {
        categoryDocumentRequirementService.createCategoryDocumentRequirement(dto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @PutMapping("{documentRequiredId}")
    public ResponseEntity<String> updateCategoryDocumentRequirementById(@PathVariable("documentRequiredId") Long documentRequiredId, @RequestBody CategoryDocumentRequirementRequestDTO dto) {
        dto.setId(documentRequiredId);
        categoryDocumentRequirementService.updateCategoryDocumentRequirement(dto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("{documentRequiredId}")
    public ResponseEntity<String> deleteCategoryDocumentRequirementById(@PathVariable("documentRequiredId") Long documentRequiredId){
        categoryDocumentRequirementService.deleteCategoryDocumentRequirement(documentRequiredId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
