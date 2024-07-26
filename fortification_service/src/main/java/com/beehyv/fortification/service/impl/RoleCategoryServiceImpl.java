package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.requestDto.StateRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.helper.Constants;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.manager.CategoryManager;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.fortification.manager.SourceCategoryMappingManager;
import com.beehyv.fortification.manager.StateManager;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.RoleCategoryMapper;
import com.beehyv.fortification.mapper.StateMapper;
import com.beehyv.fortification.service.RoleCategoryService;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RoleCategoryServiceImpl implements RoleCategoryService {
    private final BaseMapper<RoleCategoryResponseDto, RoleCategoryRequestDto, RoleCategory> mapper = BaseMapper.getForClass(RoleCategoryMapper.class);
    private final BaseMapper<StateResponseDto, StateRequestDto, State> stateMapper = BaseMapper.getForClass(StateMapper.class);
    @Autowired
    private RoleCategoryManager manager;
    @Autowired
    private KeycloakInfo keycloakInfo;
    private final StateManager stateManager;
    private final CategoryManager categoryManager;

    @Autowired
    SourceCategoryMappingManager sourceCategoryMappingManager;

    @Override
    public void createRoleCategory(RoleCategoryRequestDto dto) {
        RoleCategory entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public RoleCategoryResponseDto getRoleCategoryById(Long id) {
        RoleCategory entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<RoleCategoryResponseDto> getAllRoleCategories(Integer pageNumber, Integer pageSize) {
        List<RoleCategory> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public List<MenuLabResponseDto> getMenuForLab() {
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Set<String> roles = (Set<String>) userInfo.get("roles");
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();
        List<RoleCategory> roleCategories = manager.findAllByCategoryAndRoleNames(roleSplitList);
        Set<RoleCategoryState> roleCategoryStates = roleCategories
                .stream().filter(d -> d.getRoleCategoryType().name().equals(RoleCategoryType.LAB.name())).flatMap(d -> d.getRoleCategoryStates().stream())
                .collect(Collectors.toSet());
        List<State> allStates = stateManager.findAll(null, null);
        return roleCategoryStates.stream()
                .collect(Collectors.groupingBy(RoleCategoryState::getCategory, HashMap::new,
                        Collectors.groupingBy(RoleCategoryState::getState))).entrySet().stream().map(
                                d -> {
                                    Set<String> nameSet = new HashSet<>();
                                    MenuLabResponseDto menuLabResponseDto = new MenuLabResponseDto();
                                    menuLabResponseDto.setCategoryId(d.getKey().getId());
                                    menuLabResponseDto.setCategoryName(d.getKey().getName());
                                    menuLabResponseDto.setSequence(d.getKey().getSequence());
                                    List<MenuStateResponseDto> menuStates = new ArrayList<>();
                                    MenuStateResponseDto allMenu = new MenuStateResponseDto();
                                    allMenu.setDisplayName("All");
                                    allMenu.setName("all");
                                    allMenu.setIds(allStates.stream().map(State::getId).toList());
                                    menuStates.add(allMenu);
                                    menuStates.addAll(d.getValue().keySet().stream().filter(categoryStates -> nameSet.add(categoryStates.getDisplayName()))
                                                    .map(stateMapper::toDto)
                                                    .sorted(new Comparator<StateResponseDto>() {
                                                        @Override
                                                        public int compare(StateResponseDto o1, StateResponseDto o2) {
                                                            if(Objects.equals(o1.getSequence(), o2.getSequence())) return 0;
                                                            return o1.getSequence() > o2.getSequence() ? 1 : -1;
                                                        }
                                                    })
                                                    .map(rcs -> {
                                                        MenuStateResponseDto menuState = new MenuStateResponseDto();
                                                        BeanUtils.copyProperties(rcs, menuState);
                                                        List<Long> ids = new ArrayList<>();
                                                        ids.add(rcs.getId());
                                                        menuState.setIds(ids);
                                                        return menuState;
                                                    })
                                                    .toList()
                                    );
                                    menuLabResponseDto.setEntityStates(menuStates);
                                    return menuLabResponseDto;
                                }
                )
                .sorted((o1, o2)->o1.getSequence().compareTo(o2.getSequence()))
                .toList();
    }

    @Override
    public List<MenuResponseDto> getAllMenuRoleCategories() {
        Map<String, Object> userInfo = keycloakInfo.getUserInfo();
        Set<String> roles = (Set<String>) userInfo.get("roles");
//        Set<String> roles = new HashSet<>(List.of("PREMIX_USER_MODULE","FRK_USER_MODULE"));
        List<String[]> roleSplitList = roles.stream().map(r -> r.split("_")).filter(rc -> rc.length == 3).toList();
        if (isInspectionUser()) return getMenuForInspection(roleSplitList);
        List<State> allStates = stateManager.findAll(null, null);
        List<RoleCategory> roleCategories = manager.findAllByCategoryAndRoleNames(roleSplitList);
        HashMap<Long, Set<StateType>> categoryIdInvTypeMap = this.getCategoryActionTypeMap(roleCategories.stream()
                .map(RoleCategory::getCategory)
                .distinct()
                .toList());
        Set<RoleCategoryState> roleCategoryStates = roleCategories
                .stream().filter(d -> d.getRoleCategoryType().name().equals(RoleCategoryType.MODULE.name()))
                .flatMap(d -> d.getRoleCategoryStates().stream())
                .collect(Collectors.toSet());
        Set<Category> categories = new HashSet<>();
        roleCategories.forEach(d -> {
            categories.addAll(d.getCategory().getSourceCategories());
            if(d.getCategory().getSourceCategories() == null || d.getCategory().getSourceCategories().isEmpty()) {
                categories.addAll(sourceCategoryMappingManager.getSource(d.getCategory().getId()));
            }
        });

        List<MenuResponseDto> menuResponseDtos = roleCategoryStates.stream()
                .collect(Collectors.groupingBy(RoleCategoryState::getCategory, HashMap::new,
                        Collectors.groupingBy(d -> d.getState().getType())))
                .entrySet().stream().map(d -> {
                    MenuResponseDto dto = new MenuResponseDto();
                    dto.setCategoryId(d.getKey().getId());
                    dto.setCategoryName(d.getKey().getName());
                    dto.setSequence(d.getKey().getSequence());
                    dto.setEntityStates(d.getValue().entrySet().stream().map(entry -> {
                        EntityStatesResponseDto entityStatesResponseDto = new EntityStatesResponseDto();
                        entityStatesResponseDto.setName(entry.getKey().name());
                        List<MenuStateResponseDto> menuStates = new ArrayList<>();
                        List<RoleCategoryState> roleCategoryStates1 = entry.getValue()
                                .stream().collect(Collectors.groupingBy(x -> x.getState().getName(), Collectors.toList()))
                                .values().stream().map(categoryStates -> categoryStates.get(0))
                                .sorted(Comparator.comparing(o -> o.getState().getSequence())).toList();
                                if (!entry.getValue().isEmpty()) {
                            MenuStateResponseDto allMenu = new MenuStateResponseDto();
                            allMenu.setDisplayName("All");
                            allMenu.setName("all");
                            allMenu.setIds(allStates.stream().map(State::getId).toList());
                            menuStates.add(allMenu);
                            menuStates.addAll(roleCategoryStates1.stream()
                                    .map(rcs -> stateMapper.toDto(rcs.getState()))
                                    .sorted((o1, o2) -> {
                                        if (Objects.equals(o1.getSequence(), o2.getSequence())) return 0;
                                        return o1.getSequence() > o2.getSequence() ? 1 : -1;
                                            })
                                    .map(rcs -> {
                                        MenuStateResponseDto menuState = new MenuStateResponseDto();
                                        BeanUtils.copyProperties(rcs, menuState);
                                        List<Long> ids = new ArrayList<>();
                                        ids.add(rcs.getId());
                                        menuState.setIds(ids);
                                        return menuState;
                                    })
                                    .toList()
                            );
                        }
                        entityStatesResponseDto.setStates(menuStates);
                        entityStatesResponseDto.setInventory(categoryIdInvTypeMap.get(d.getKey().getId()).contains(entry.getKey()));
                        return entityStatesResponseDto;
                    })
                            .filter(cat -> !cat.getStates().isEmpty())
                            .sorted(Comparator.comparing(EntityStatesResponseDto::getName))
                            .collect(Collectors.toList()));
                    return dto;
                })
                .filter(d -> !d.getEntityStates().isEmpty())
                .collect(Collectors.toList());
        menuResponseDtos.addAll(categories.stream().filter(Category::isIndependentBatch).map(d -> {
            MenuResponseDto menuResponseDto = new MenuResponseDto();
            menuResponseDto.setCategoryId(d.getId());
            menuResponseDto.setCategoryName(d.getName());
            menuResponseDto.setSequence(d.getSequence());
            menuResponseDto.setIndependentBatch(d.isIndependentBatch());
            EntityStatesResponseDto entityStatesResponseDto = new EntityStatesResponseDto();
            entityStatesResponseDto.setName(StateType.LOT.name());
            entityStatesResponseDto.setInventory(true);
            entityStatesResponseDto.setStates(new ArrayList<>());
            MenuStateResponseDto allMenu = new MenuStateResponseDto();
            allMenu.setDisplayName("All");
            allMenu.setName("all");
            allMenu.setIds(allStates.stream().map(State::getId).toList());
            entityStatesResponseDto.getStates().add(allMenu);
            List<EntityStatesResponseDto> entityStatesResponseDtos = new ArrayList<>();
            entityStatesResponseDtos.add(entityStatesResponseDto);
            menuResponseDto.setEntityStates(entityStatesResponseDtos);
            return menuResponseDto;
        }).toList());
        return menuResponseDtos.stream().sorted(Comparator.comparing(MenuResponseDto::getSequence)).toList();
    }

    @Override
    public void updateRoleCategory(RoleCategoryRequestDto dto) {
        RoleCategory existingRoleCategory = manager.findById(dto.getId());
        RoleCategory entity = mapper.toEntity(dto);
        entity.setUuid(existingRoleCategory.getUuid());
        manager.update(entity);
    }

    @Override
    public void deleteRoleCategory(Long id) {
        manager.findById(id);
        manager.delete(id);
    }

    private boolean isInspectionUser() {
        Set<String> userRoles = (Set<String>) keycloakInfo.getUserInfo().get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("INSPECTION"));
    }

    private HashMap<Long, Set<StateType>> getCategoryActionTypeMap(List<Category> categories) {
        HashMap<Long, Set<StateType>> categoryIdInvTypeMap = new HashMap<>();
        categories.forEach(d -> {
            if (categoryIdInvTypeMap.containsKey(d.getId())) {
                categoryIdInvTypeMap.get(d.getId()).add(StateType.BATCH);
            } else {
                categoryIdInvTypeMap.put(d.getId(), new HashSet<>(List.of(StateType.BATCH)));
            }
            d.getSourceCategories().forEach(d1 -> {
                if (categoryIdInvTypeMap.containsKey(d1.getId())) {
                    categoryIdInvTypeMap.get(d1.getId()).add(StateType.LOT);
                } else {
                    categoryIdInvTypeMap.put(d1.getId(), new HashSet<>(List.of(StateType.LOT)));
                }
            });
            if (d.getSourceCategories().isEmpty()) {
                sourceCategoryMappingManager.getSource(d.getId()).forEach(
                        d1 -> {
                            if (categoryIdInvTypeMap.containsKey(d1.getId())) {
                                categoryIdInvTypeMap.get(d1.getId()).add(StateType.LOT);
                            } else {
                                categoryIdInvTypeMap.put(d1.getId(), new HashSet<>(List.of(StateType.LOT)));
                            }
                        }
                );
            }
        });
        return categoryIdInvTypeMap;
    }

    private List<MenuResponseDto> getMenuForInspection(List<String[]> roleSplitList) {
        List<Category> categories = categoryManager.findAllByNames(roleSplitList.stream().map(r -> r[0]).toList());
        List<MenuResponseDto> menuResponseDtos = new ArrayList<>();
        HashMap<Long, Set<StateType>> categoryIdInspectionTypeMap = getCategoryActionTypeMap(categories);
        categories.forEach(c -> {
            MenuResponseDto dto = new MenuResponseDto();
            dto.setCategoryId(c.getId());
            dto.setCategoryName(c.getName());
            dto.setIndependentBatch(c.isIndependentBatch());
            dto.setEntityStates(getEntityStatesForInspection(categoryIdInspectionTypeMap, c));
            menuResponseDtos.add(dto);
        });
        return menuResponseDtos;
    }

    private List<EntityStatesResponseDto> getEntityStatesForInspection(HashMap<Long, Set<StateType>> categoryIdInvTypeMap, Category category) {
        EntityStatesResponseDto batchEntityStates = new EntityStatesResponseDto();
        batchEntityStates.setName(StateType.BATCH.name());
        batchEntityStates.setInventory(false);
        batchEntityStates.setStates(getMenuStatesForInspection(StateType.BATCH));
        batchEntityStates.setCanInspect(categoryIdInvTypeMap.get(category.getId()).contains(StateType.BATCH));
        EntityStatesResponseDto lotEntityStates = new EntityStatesResponseDto();
        lotEntityStates.setName(StateType.LOT.name());
        lotEntityStates.setInventory(false);
        lotEntityStates.setStates(getMenuStatesForInspection(StateType.LOT));
        lotEntityStates.setCanInspect(categoryIdInvTypeMap.get(category.getId()).contains(StateType.LOT));
        return List.of(batchEntityStates, lotEntityStates);
    }

    private List<MenuStateResponseDto> getMenuStatesForInspection(StateType type) {
        List<String> displayNames = new ArrayList<>();
        List<State> labStates = stateManager.findAllByActionType(ActionType.lab);
        displayNames.add("ALL");
        displayNames.addAll(labStates.stream().map(State::getDisplayName).distinct().toList());
        return displayNames.stream()
                .map(d -> new MenuStateResponseDto(null, type, null, null, d, null))
                .toList();
    }

}
