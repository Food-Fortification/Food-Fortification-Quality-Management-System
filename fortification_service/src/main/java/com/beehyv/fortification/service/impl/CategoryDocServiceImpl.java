package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.CategoryDoc;
import com.beehyv.fortification.entity.DocType;
import com.beehyv.fortification.manager.CategoryDocManager;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.CategoryDocMapper;
import com.beehyv.fortification.service.CategoryDocService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CategoryDocServiceImpl implements CategoryDocService {

    private final BaseMapper<CategoryDocResponseDto, CategoryDocRequestDto, CategoryDoc> mapper = BaseMapper.getForClass(CategoryDocMapper.class) ;
    private CategoryDocManager manager;

    @Override
    public ListResponse<CategoryDocResponseDto> getRequiredDocByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize) {
        List<CategoryDoc> entities = manager.findAllByCategoryId(categoryId, pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), categoryId, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void createCategoryDoc(CategoryDocRequestDto dto) {
        CategoryDoc entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public CategoryDocResponseDto getCategoryDocById(Long id) {
        CategoryDoc entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<CategoryDocResponseDto> getAllCategoryDocs(Integer pageNumber, Integer pageSize) {
        List<CategoryDoc> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateCategoryDoc(CategoryDocRequestDto dto) {
        CategoryDoc existingCategoryDoc = manager.findById(dto.getId());
        Category category = new Category();
        category.setId(dto.getCategoryId());

        DocType docType = new DocType();
        docType.setId(dto.getDocTypeId());

        existingCategoryDoc.setCategory(category);
        existingCategoryDoc.setDocType(docType);
        existingCategoryDoc.setIsMandatory(dto.getIsMandatory());
        existingCategoryDoc.setIsEnabled(dto.getIsEnabled() == null || dto.getIsEnabled());
        manager.update(existingCategoryDoc);
    }

    @Override
    public void deleteCategoryDoc(Long id) {
        manager.delete(id);
    }
}
