package org.path.fortification.manager;

import org.path.fortification.dao.RoleCategoryDao;
import org.path.fortification.entity.RoleCategory;
import org.path.fortification.entity.RoleCategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RoleCategoryManager extends BaseManager<RoleCategory, RoleCategoryDao> {
    @Autowired
    private RoleCategoryDao roleCategoryDao;

    public RoleCategoryManager(RoleCategoryDao dao) {
        super(dao);
        this.roleCategoryDao = dao;
    }

    public List<RoleCategory> findAllByIdAndRoleCategoryType(Set<Long> roleCategoryIds, RoleCategoryType roleCategoryType) {
        return roleCategoryDao.findAllByIdAndRoleCategoryType(roleCategoryIds, roleCategoryType);
    }

    public List<RoleCategory> findAllByCategoryIdAndState(Long categoryId, Long stateId) {
        return roleCategoryDao.findByCategoryAndState(categoryId, stateId);
    }

    public List<List<RoleCategory>> findAllByCategoryName(List<String[]> roleCategoryList) {
        List<List<RoleCategory>> roleCategoriesList = new ArrayList<>();
        for (String[] roleCategoryArray : roleCategoryList) {
            List<RoleCategory> roleCategories;
            roleCategories = roleCategoryDao.findByCategoryName(roleCategoryArray[0], RoleCategoryType.valueOf(roleCategoryArray[2]));
            if (!roleCategories.isEmpty()) roleCategoriesList.add(roleCategories);
        }
        return roleCategoriesList;
    }

    public List<RoleCategory> findAllByIds(List<Long> ids) {
        return roleCategoryDao.findAllByIds(ids);
    }

    public List<RoleCategory> findAllByCategoryAndRoleNames(List<String[]> roleCategoryList) {
        List<RoleCategory> roleCategories = new ArrayList<>();
        for (String[] roleCategoryArray : roleCategoryList) {
            RoleCategory roleCategory = roleCategoryDao.findByRoleAndCategoryNames(roleCategoryArray[1], roleCategoryArray[0], RoleCategoryType.valueOf(roleCategoryArray[2]));
            if (roleCategory != null) roleCategories.add(roleCategory);
        }
        return roleCategories;
    }

    public RoleCategory findByCategoryAndRoleNames(String category, String role, RoleCategoryType type) {
        List<RoleCategory> roleCategories = roleCategoryDao.findListByRoleAndCategoryNames(role, category, type);
        if (roleCategories.isEmpty()) {
            return null;
        } else {
            return roleCategories.get(0);
        }
    }

    public List<List<RoleCategory>> findAllByCategoryIdAndType(Map<String, List<Long>> roleTypeList) {
        List<List<RoleCategory>> roleCategoriesList = new ArrayList<>();
        RoleCategoryType type = RoleCategoryType.valueOf(roleTypeList.keySet().stream().findFirst().get());
        List<Long> categoryIds = roleTypeList.get(roleTypeList.keySet().stream().findFirst().get());
        for (Long categoryId : categoryIds) {
            List<RoleCategory> roleCategories;
            roleCategories = roleCategoryDao.findByCategoryIdAndType(categoryId, type);
            if (!roleCategories.isEmpty()) roleCategoriesList.add(roleCategories);
        }
        return roleCategoriesList;
    }
}
