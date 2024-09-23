package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.iam.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.manager.CategoryDocManager;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.mapper.CategoryDocMapper;
import com.beehyv.iam.model.CategoryDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryDocService {
    private final BaseMapper<CategoryDocResponseDto, CategoryDocRequestDto, CategoryDoc> categoryDocMapper = BaseMapper.getForClass(CategoryDocMapper.class) ;
    private final CategoryDocManager manager;

    public ListResponse<CategoryDocResponseDto> getRequiredDocByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize) {
        List<CategoryDoc> entities = manager.findAllByCategoryId(categoryId, pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), categoryId, pageNumber, pageSize);
        return ListResponse.from(entities, categoryDocMapper::toDto, count);
    }

    public void createCategoryDoc(CategoryDocRequestDto dto) {
        CategoryDoc entity = categoryDocMapper.toEntity(dto);
        manager.create(entity);
    }

    public CategoryDocResponseDto getCategoryDocById(Long id) {
        CategoryDoc entity = manager.findById(id);
        return categoryDocMapper.toDto(entity);
    }

    public ListResponse<CategoryDocResponseDto> getAllCategoryDocs(Integer pageNumber, Integer pageSize) {
        List<CategoryDoc> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, categoryDocMapper::toDto, count);
    }

    public void update(CategoryDocRequestDto dto) {
        manager.update(categoryDocMapper.toEntity(dto));
    }

    public void deleteCategoryDoc(Long id) {
        manager.delete(id);
    }
}
