package org.path.lab.service;

import org.path.lab.dto.requestDto.CategoryDocumentRequirementRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.CategoryDocumentRequirementResponseDTO;
import org.path.lab.enums.CategoryDocRequirementType;

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
