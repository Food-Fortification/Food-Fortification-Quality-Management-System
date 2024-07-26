package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.LabConfigCategoryDao;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.LabConfigCategory;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabConfigCategoryManager extends BaseManager<LabConfigCategory, LabConfigCategoryDao> {
    private final LabConfigCategoryDao dao;
    private final KeycloakInfo keycloakInfo;

    public LabConfigCategoryManager(LabConfigCategoryDao dao, KeycloakInfo keycloakInfo) {
        super(dao);
        this.dao = dao;
        this.keycloakInfo = keycloakInfo;
    }


    public LabConfigCategory findByCategory(Category category) {
        return dao.findByCategoryId(category.getId());
    }

    public LabConfigCategory findByCategoryIds(Long categoryId, Long targetCategoryId) {
        List<LabConfigCategory> configs = dao.findByCategoryIds(categoryId, targetCategoryId);
        if (configs.isEmpty()) {
            return null;
        } else {
            return configs.get(0);
        }
    }
}
