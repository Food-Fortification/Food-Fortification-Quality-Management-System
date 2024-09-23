package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.CategoryDao;
import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.RoleCategoryType;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;


@Component
public class CategoryManager extends BaseManager<Category, CategoryDao> {
    private final CategoryDao dao;
    private final KeycloakInfo keycloakInfo;
    public CategoryManager(CategoryDao dao, KeycloakInfo keycloakInfo) {
        super(dao);
        this.dao = dao;
        this.keycloakInfo = keycloakInfo;
    }

    public List<Category> getCategoryBySourceCategoryId(Long categoryId, Long stateGeoId) {
        return dao.getCategoryBySourceCategoryId(categoryId, stateGeoId);
    }

    public Map<Long, String> getCategoryAndActionBySourceCategoryId(Long categoryId, Long stateGeoId) {
        return dao.getCategoryAndActionBySourceCategoryId(categoryId, stateGeoId);
    }

    public List<Category> findAllByIds(List<Long> ids) {
        return dao.findAllByIds(ids);
    }

    public List<Category> findAllByNames(List<String> categoryNames) {
        return dao.findAllByNames(categoryNames);
    }

    public List<Category> findAllByIndependentBatch(Boolean independentBatch, Integer pageNumber, Integer pageSize) {
        return dao.findAllByIndependentBatch(independentBatch, pageNumber, pageSize);
    }

    public List<String> findAllNamesByIndependentBatch(Boolean independentBatch) {
        return dao.findAllNamesByIndependentBatch(independentBatch);
    }
    public Long getCountByIndependentBatch(int size, Boolean independentBatch, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) size).longValue();
        }
        return dao.getCountByIndependentBatch(independentBatch);
    }

    public List<Category> findAllBySuperUser(String roleCategoryType) {
        List<String> categories = findAllNamesByIndependentBatch(false);
        List<String> rolesList = new ArrayList<>();
        categories.forEach(name -> rolesList.add(name.toUpperCase() + "_SUPERADMIN"));
        rolesList.stream().map(d -> d + "_" + roleCategoryType).toList();
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String> superList = roles.stream().filter(rolesList::contains).toList();
        List<String> categoryNames = superList.stream().map(d -> d.split("_")[0]).toList();
        return this.findAllByNames(categoryNames);
    }

    public boolean isCategorySuperAdmin(Long categoryId, RoleCategoryType roleCategoryType) {
        Category category = this.findById(categoryId);
        List<String> categories = findAllNamesByIndependentBatch(false);
        List<String> rolesList = new ArrayList<>();
        categories.forEach(name -> {
            rolesList.add(name.toUpperCase() + "_SUPERADMIN_MODULE");
            rolesList.add(name.toUpperCase() + "_SUPERADMIN_LAB");
        });
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String> superList = roles.stream().filter(rolesList::contains).toList();
        return superList.stream().anyMatch(d -> d.equals(
                String.format("%s_SUPERADMIN_%s", category.getName(), roleCategoryType.name())
        ));
    }

    public boolean isCategoryInspectionUser(Long categoryId, RoleCategoryType roleCategoryType) {
        Category category = this.findById(categoryId);

        List<String> categories = findAllNamesByIndependentBatch(false);
        List<String> rolesList = new ArrayList<>();
        categories.forEach(name -> {
            rolesList.add(name.toUpperCase() + "_INSPECTION_MODULE");
        });
        Set<String> roles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        List<String> superList = roles.stream().filter(rolesList::contains).toList();
        return superList.stream().anyMatch(d -> d.equals(
                String.format("%s_INSPECTION_%s", category.getName(), roleCategoryType.name())
        ));
    }

    public List<Long> getUserCategoryIds(SearchListRequest searchRequest, RoleCategoryType roleCategoryType) {
        Optional<List<Long>> categoryIdsOptional = Optional.empty();
        if (searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            try {
                categoryIdsOptional = searchRequest.getSearchCriteriaList()
                        .stream().filter(d -> d.getKey().equals("categoryIds"))
                        .map(d -> (List<Long>) ((List) d.getValue()).stream().map(d1 -> Long.parseLong(d1.toString())).toList())
                        .findAny();
            } catch (Exception ignore) {
            }

        }
        if(categoryIdsOptional.isEmpty()) {
            categoryIdsOptional = Optional.of(this.findAllBySuperUser(roleCategoryType.name()).stream().map(Category::getId).toList());
        }
        return categoryIdsOptional.get();
    }

    public List<Category> getSourceCategory(Long targetCategoryId, Long geoId) {
        return dao.getSourceCategory(targetCategoryId, geoId);
    }

    public Long findCategoryIdByName(String categoryName){
        return dao.findCategoryIdByName(categoryName);
    }

    public Category findCategoryByName(String categoryName){
        return dao.findCategoryByName(categoryName);
    }

    public String findCategoryNameById(Long categoryId){
        return dao.findCategoryNameById(categoryId);
    }

    public List<Category> findCategoryListByName(String categoryName){
        return dao.findCategoryListByName(categoryName);
    }

    public Integer findSequence() {
        return dao.findMaxSequence();
    }
}
