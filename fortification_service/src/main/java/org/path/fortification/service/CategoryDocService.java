package org.path.fortification.service;

import org.path.fortification.dto.requestDto.CategoryDocRequestDto;
import org.path.fortification.dto.responseDto.CategoryDocResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;


public interface CategoryDocService {

    ListResponse<CategoryDocResponseDto> getRequiredDocByCategoryId(Long id, Integer pageNumber, Integer pageSize);

    void createCategoryDoc(CategoryDocRequestDto dto);

    CategoryDocResponseDto getCategoryDocById(Long id);

    ListResponse<CategoryDocResponseDto> getAllCategoryDocs(Integer pageNumber, Integer pageSize);

    void updateCategoryDoc(CategoryDocRequestDto dto);

    void deleteCategoryDoc(Long id);
}
