package org.path.fortification.service;

import org.path.fortification.dto.requestDto.CategoryRequestDto;
import org.path.fortification.dto.responseDto.CategoryResponseDto;
import org.path.fortification.dto.responseDto.CategoryRoleResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    void createCategory(CategoryRequestDto dto);

    CategoryResponseDto getCategoryById(Long id);

    ListResponse<CategoryResponseDto> getAllCategories(Boolean independentBatch, Integer pageNumber, Integer pageSize);

    void updateCategory(CategoryRequestDto dto);

    void deleteCategory(Long id);

    List<Long> getNextCategoryIdById(Long id, Long stateGeoId);

    List<CategoryResponseDto> getAllCategoriesForManufacturer(Boolean withSources);

    Map<String, CategoryRoleResponseDto> getAllCategoryRolesForManufacturer();

    Map<String, CategoryRoleResponseDto> getAllCategoryRolesForManufacturer(Map<String, List<Long>> roleTypeList);

    Long getCategoryIdByName(String categoryName);
    Map<Long, String> getNextCategoryIdsAndActions(Long id, Long stateGeoId);
}
