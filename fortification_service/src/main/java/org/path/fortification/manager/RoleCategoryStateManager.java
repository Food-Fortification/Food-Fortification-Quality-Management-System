package org.path.fortification.manager;

import org.path.fortification.dao.RoleCategoryStateDao;
import org.path.fortification.entity.RoleCategoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleCategoryStateManager extends BaseManager<RoleCategoryState, RoleCategoryStateDao> {

    @Autowired
    private RoleCategoryStateDao roleCategoryStateDao;

    public RoleCategoryStateManager(RoleCategoryStateDao dao) {
        super(dao);
        this.roleCategoryStateDao = dao;
    }

    public RoleCategoryState findByIdState(Long categoryId, Long roleCategoryId, Long stateId) {
        List<RoleCategoryState> roleCategoryStateList =
                roleCategoryStateDao.findByIds(categoryId, roleCategoryId, stateId);
        if (roleCategoryStateList.isEmpty()) return null;
        return roleCategoryStateList.get(0);
    }
}
