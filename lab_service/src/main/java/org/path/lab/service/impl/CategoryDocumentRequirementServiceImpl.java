package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.CategoryDocumentRequirementRequestDTO;
import org.path.lab.dto.responseDto.CategoryDocumentRequirementResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.CategoryDocumentRequirement;
import org.path.lab.enums.CategoryDocRequirementType;
import org.path.lab.manager.CategoryDocumentRequirementManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.CategoryDocumentRequirementService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class CategoryDocumentRequirementServiceImpl implements CategoryDocumentRequirementService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final CategoryDocumentRequirementManager manager;

    @Override
    public ListResponse<CategoryDocumentRequirementResponseDTO> getAllCategoryDocumentRequirements(Integer pageNumber, Integer pageSize) {
        List<CategoryDocumentRequirement> categoryDocumentRequirements = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(categoryDocumentRequirements.size(), pageNumber, pageSize);
        return ListResponse.from(categoryDocumentRequirements, mapper::mapEntityToDtoCategoryDocumentRequirement, count);
    }

    @Override
    public List<CategoryDocumentRequirementResponseDTO> getAllCategoryDocumentRequirementsByCategoryIdAndType(Long categoryId, CategoryDocRequirementType docRequirementType, Integer pageNumber, Integer pageSize) {
        List<CategoryDocumentRequirement> categoryDocumentRequirements = manager.findAllByCategoryIdAndType(categoryId, docRequirementType, pageNumber, pageSize);
        return categoryDocumentRequirements.stream().map(mapper::mapEntityToDtoCategoryDocumentRequirement).collect(Collectors.toList());
    }

    @Override
    public void createCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO CategoryDocumentRequirementRequestDTO) {
        CategoryDocumentRequirement entity = mapper.mapDtoToEntityCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO);
        if(entity.getDocType().getId()==null){
            entity.setDocType(null);
        }
        manager.create(entity);
    }

    @Override
    public CategoryDocumentRequirementResponseDTO getCategoryDocumentRequirementById(Long id) {
        CategoryDocumentRequirement categoryDocumentRequirement = manager.findById(id);
        if(categoryDocumentRequirement != null) {
            return mapper.mapEntityToDtoCategoryDocumentRequirement(categoryDocumentRequirement);
        } else {
            return null;
        }
    }

    @Override
    public void updateCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO CategoryDocumentRequirementRequestDTO) {
        CategoryDocumentRequirement categoryDocumentRequirement = manager.findById(CategoryDocumentRequirementRequestDTO.getId());
        if (categoryDocumentRequirement != null){
            CategoryDocumentRequirement categoryDocumentRequirementUpdate = mapper.mapDtoToEntityCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO);
            categoryDocumentRequirementUpdate.setUuid(categoryDocumentRequirement.getUuid());
            manager.update(categoryDocumentRequirementUpdate);
        }
    }

    @Override
    public void deleteCategoryDocumentRequirement(Long id) {
        CategoryDocumentRequirement categoryDocumentRequirement = manager.findById(id);
        if (categoryDocumentRequirement != null) {
            manager.delete(id);
        }
    }

    @Override
    public ListResponse<CategoryDocumentRequirementResponseDTO> getAllCategoryDocumentRequirementsByType(CategoryDocRequirementType categoryDocRequirementType, Integer pageNumber, Integer pageSize) {
        List<CategoryDocumentRequirement> categoryDocumentRequirements = manager.findAll(categoryDocRequirementType, pageNumber, pageSize);
        Long count = manager.getCount(categoryDocumentRequirements.size(), categoryDocRequirementType, pageNumber, pageSize);
        return ListResponse.from(categoryDocumentRequirements, mapper::mapEntityToDtoCategoryDocumentRequirement, count);
    }
}
