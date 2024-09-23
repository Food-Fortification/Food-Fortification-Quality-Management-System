package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;


public interface CategoryDocService {

    ListResponse<CategoryDocResponseDto> getRequiredDocByCategoryId(Long id, Integer pageNumber, Integer pageSize);

    void createCategoryDoc(CategoryDocRequestDto dto);

    CategoryDocResponseDto getCategoryDocById(Long id);

    ListResponse<CategoryDocResponseDto> getAllCategoryDocs(Integer pageNumber, Integer pageSize);

    void updateCategoryDoc(CategoryDocRequestDto dto);

    void deleteCategoryDoc(Long id);
}
