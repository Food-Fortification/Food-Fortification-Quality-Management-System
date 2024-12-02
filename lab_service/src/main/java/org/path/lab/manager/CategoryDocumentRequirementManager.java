package org.path.lab.manager;

import org.path.lab.entity.CategoryDocumentRequirement;
import org.path.lab.dao.CategoryDocumentRequirementDao;
import org.path.lab.enums.CategoryDocRequirementType;
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
