package org.path.fortification.manager;

import org.path.fortification.dao.SourceCategoryMappingDao;
import org.path.fortification.entity.Category;
import org.path.fortification.entity.SourceCategoryMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SourceCategoryMappingManager extends BaseManager<SourceCategoryMapping, SourceCategoryMappingDao> {

    @Autowired
    private SourceCategoryMappingDao sourceCategoryMappingDao;

    public SourceCategoryMappingManager(SourceCategoryMappingDao dao) {
        super(dao);
        this.sourceCategoryMappingDao = dao;
    }

    public SourceCategoryMapping findByIds(Long returnId, Long sourceId, Long targetId) {
        List<SourceCategoryMapping> sourceCategoryMappingList =
                sourceCategoryMappingDao.findByIds(returnId, sourceId, targetId);
        if (sourceCategoryMappingList.isEmpty()) return null;
        return sourceCategoryMappingList.get(0);
    }

    public List<Category> getSource(Long sourceId) {
        List<SourceCategoryMapping> sourceCategoryMappingList =
                sourceCategoryMappingDao.findBySourceId(sourceId);
        return sourceCategoryMappingList.stream().map(SourceCategoryMapping::getReturnCategory).toList();
    }
}
