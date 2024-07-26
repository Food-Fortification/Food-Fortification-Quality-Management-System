package com.beehyv.lab.manager;

import com.beehyv.lab.entity.CategoryDocumentRequirement;
import com.beehyv.lab.dao.CategoryDocumentRequirementDao;
import com.beehyv.lab.enums.CategoryDocRequirementType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDocumentRequirementManager extends BaseManager<CategoryDocumentRequirement, CategoryDocumentRequirementDao>{

    private final CategoryDocumentRequirementDao dao;
    public CategoryDocumentRequirementManager(CategoryDocumentRequirementDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<CategoryDocumentRequirement> findAllByCategoryIdAndType(Long categoryId, CategoryDocRequirementType docRequirementType, Integer pageNumber, Integer pageSize) {
        return dao.findAllByCategoryIdAndType(categoryId, docRequirementType, pageNumber, pageSize);
    }

    public List<CategoryDocumentRequirement> findAll(CategoryDocRequirementType categoryDocRequirementType, Integer pageNumber, Integer pageSize) {
        return dao.findAll(categoryDocRequirementType, pageNumber, pageSize);
    }

    public Long getCount(int size, CategoryDocRequirementType categoryDocRequirementType, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.getCount(categoryDocRequirementType);
    }
}
