package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.CategoryRequestDto;
import org.path.fortification.dto.responseDto.CategoryResponseDto;
import org.path.fortification.dto.responseDto.CategoryRoleResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.entity.Category;
import org.path.fortification.entity.RoleCategory;
import org.path.fortification.manager.CategoryManager;
import org.path.fortification.manager.RoleCategoryManager;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.CategoryMapper;
import org.path.fortification.service.CategoryService;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final BaseMapper<CategoryResponseDto, CategoryRequestDto, Category> mapper = BaseMapper.getForClass(CategoryMapper.class);
    private CategoryManager manager;
    @Autowired
    private KeycloakInfo keycloakInfo;
    private RoleCategoryManager roleCategoryManager;

    @Override
    public void createCategory(CategoryRequestDto dto) {
        Category entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<CategoryResponseDto> getAllCategories(Boolean independentBatch, Integer pageNumber, Integer pageSize) {
        List<Category> entities = manager.findAllByIndependentBatch(independentBatch, pageNumber, pageSize);
        Long count = manager.getCountByIndependentBatch(entities.size(), independentBatch, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateCategory(CategoryRequestDto dto) {
        Category existingCategory = manager.findById(dto.getId());
        Category entity = mapper.toEntity(dto);
        manager.update(entity);
    }

    @Override
    public void deleteCategory(Long id) {
        manager.delete(id);
    }

    @Override
    public List<Long> getNextCategoryIdById(Long id, Long stateGeoId) {
        return manager.getCategoryBySourceCategoryId(id, stateGeoId).stream().map(Category::getId).toList();
    }

    @Override
    public List<CategoryResponseDto> getAllCategoriesForManufacturer(Boolean withSources) {
        List<String[]> roleSplitList = getRoleCategories();
        List<RoleCategory> roleCategories = roleCategoryManager.findAllByCategoryAndRoleNames(roleSplitList);
        List<Category> categories = roleCategories.stream().map(RoleCategory::getCategory).toList();
        return new HashSet<>(categories)
                .stream()
                .map(mapper::toDto)
                .peek(c -> {
                    if (!withSources)
                        c.setSourceCategories(null);
                    c.setDocuments(null);
                })
                .toList();
    }

    @Override
    public Map<String, CategoryRoleResponseDto> getAllCategoryRolesForManufacturer() {
        List<String[]> roleSplitList = getRoleCategories();
        List<List<RoleCategory>> roleCategoriesList = roleCategoryManager.findAllByCategoryName(roleSplitList);
        return this.roleCategoryHelper(roleCategoriesList, false);
    }
    @Override
    public Map<String, CategoryRoleResponseDto> getAllCategoryRolesForManufacturer(Map<String, List<Long>> roleTypeList) {
        List<List<RoleCategory>> roleCategoriesList = roleCategoryManager.findAllByCategoryIdAndType(roleTypeList);
        return this.roleCategoryHelper(roleCategoriesList, true);
    }

    public Map<String, CategoryRoleResponseDto> roleCategoryHelper(List<List<RoleCategory>> roleCategoriesList, boolean isSuperadmin) {
        Map<String, CategoryRoleResponseDto> result = new HashMap<>();
        Map<Category, List<RoleCategory>> roleCategoriesGroup = roleCategoriesList.stream()
                .flatMap(Collection::stream).collect(Collectors.groupingBy(RoleCategory::getCategory, Collectors.toList()));
        roleCategoriesGroup.forEach((c, rc) -> {
            CategoryRoleResponseDto categoryRoleResponseDto = new CategoryRoleResponseDto();
            categoryRoleResponseDto.setId(c.getId());
            List<String> roles = rc.stream().map(RoleCategory::getRoleName).filter(r -> !r.equals("SUPERADMIN")).distinct().toList();
            if(!isSuperadmin){
                roles = roles.stream().filter(r -> !r.equals("ADMIN")).toList();
            }
            categoryRoleResponseDto.setRoles(roles);
            result.put(c.getName(), categoryRoleResponseDto);
        });
        return result;
    }

    public List<String[]> getRoleCategories() {
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Set<String> roles = (Set<String>) userInfo.get("roles");
        return roles.stream().map(r -> r.split("_")).toList();
    }

    @Override
    public Long getCategoryIdByName(String categoryName){
        return manager.findCategoryIdByName(categoryName);
    }

    @Override
    public Map<Long, String> getNextCategoryIdsAndActions(Long id, Long stateGeoId) {
        return manager.getCategoryAndActionBySourceCategoryId(id, stateGeoId);
    }

}
