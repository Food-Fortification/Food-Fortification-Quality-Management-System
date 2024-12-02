package org.path.iam.service;

import org.path.iam.dto.requestDto.CategoryDocRequestDto;
import org.path.iam.dto.responseDto.CategoryDocResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.manager.CategoryDocManager;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.CategoryDocMapper;
import org.path.iam.model.CategoryDoc;
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
