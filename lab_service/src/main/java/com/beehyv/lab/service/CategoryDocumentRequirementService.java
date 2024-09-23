package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.CategoryDocumentRequirementRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.CategoryDocumentRequirementResponseDTO;
import com.beehyv.lab.enums.CategoryDocRequirementType;

import java.util.List;

public interface CategoryDocumentRequirementService {
    ListResponse<CategoryDocumentRequirementResponseDTO> getAllCategoryDocumentRequirements(Integer pageNumber, Integer pageSize);

    List<CategoryDocumentRequirementResponseDTO> getAllCategoryDocumentRequirementsByCategoryIdAndType(Long categoryId, CategoryDocRequirementType test, Integer pageNumber, Integer pageSize);

    void createCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO dto);

    CategoryDocumentRequirementResponseDTO getCategoryDocumentRequirementById(Long id);

    void updateCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO categoryDocumentRequirementRequestDTO);

    void deleteCategoryDocumentRequirement(Long id);

    ListResponse<CategoryDocumentRequirementResponseDTO> getAllCategoryDocumentRequirementsByType(CategoryDocRequirementType categoryDocRequirementType, Integer pageNumber, Integer pageSize);
}
