package org.path.iam.manager;

import org.path.iam.dao.CategoryDocDao;
import org.path.iam.model.CategoryDoc;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CategoryDocManager extends BaseManager<CategoryDoc, CategoryDocDao>{
    private final CategoryDocDao dao;
    public CategoryDocManager(CategoryDocDao dao) {
        super(dao);
        this.dao = dao;
    }
    public List<CategoryDoc> findAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByCategoryId(categoryId, pageNumber, pageSize);
    }

    public Long getCount(Integer listSize, Long categoryId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount(categoryId);
    }
}
