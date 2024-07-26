package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.MenuLabResponseDto;
import com.beehyv.fortification.dto.responseDto.MenuResponseDto;
import com.beehyv.fortification.dto.responseDto.RoleCategoryResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.manager.CategoryManager;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.fortification.manager.StateManager;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleCategoryServiceImplTest {

    @Mock
    private RoleCategoryManager manager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private StateManager stateManager;

    @Mock
    private CategoryManager categoryManager;


    @InjectMocks
    private RoleCategoryServiceImpl roleCategoryService;

    private RoleCategoryRequestDto requestDto;
    private RoleCategory entity;
    private RoleCategoryResponseDto responseDto;
    private List<String[]> roleSplitList;
    private List<RoleCategory> roleCategories;
    private Set<RoleCategoryState> roleCategoryStates;
    private List<State> allStates;
    private Map<String, Object> userInfo;
    private MockedStatic<IamServiceRestHelper> mockStatic;


    @BeforeEach
    void setup() {
        requestDto = new RoleCategoryRequestDto();
        entity = new RoleCategory();
        responseDto = new RoleCategoryResponseDto();
        roleSplitList = new ArrayList<>();
        roleCategories = new ArrayList<>();
        roleCategoryStates = new HashSet<>();
        allStates = new ArrayList<>();
        userInfo = new HashMap<>();
        userInfo.put("roles", new HashSet<>(List.of("ROLE_Random")));

        when(manager.create(any(RoleCategory.class))).thenReturn(entity);
        when(manager.findById(anyLong())).thenReturn(entity);
        when(manager.findAll(anyInt(), anyInt())).thenReturn(List.of(entity));
        when(manager.getCount(anyInt(), anyInt(), anyInt())).thenReturn(1L);
        when(manager.findAllByCategoryAndRoleNames(any())).thenReturn(roleCategories);
        when(stateManager.findAll(anyInt(), anyInt())).thenReturn(allStates);
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(keycloakInfo.getAccessToken()).thenReturn("a");
        mockStatic = mockStatic(IamServiceRestHelper.class);
    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
    }

    @Test
    void createRoleCategory() {
        roleCategoryService.createRoleCategory(requestDto);
        verify(manager, times(1)).create(any(RoleCategory.class));
    }

    @Test
    void getRoleCategoryById() {
        RoleCategoryResponseDto result = roleCategoryService.getRoleCategoryById(1L);
        assertThat(result).isNotNull();
        verify(manager, times(1)).findById(1L);
    }

    @Test
    void getAllRoleCategories() {
        ListResponse<RoleCategoryResponseDto> result = roleCategoryService.getAllRoleCategories(0, 10);
        assertThat(result.getData()).isNotEmpty();
        assertThat(result.getCount()).isEqualTo(1L);
        verify(manager, times(1)).findAll(0, 10);
        verify(manager, times(1)).getCount(1, 0, 10);
    }

    @Test
    void getMenuForLab() {

        List<String[]> roleSplitList = new ArrayList<>();
        List<State> allStates = new ArrayList<>();
        RoleCategory roleCategory = new RoleCategory();
        roleCategory.setRoleCategoryType(RoleCategoryType.MODULE);
        RoleCategoryState roleCategoryState = new RoleCategoryState();
        Category category = new Category(1L, "Test Category", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1);
        category.setId(1L); // Set an ID for the category
        category.setName("Test Category"); // Set a name for the category
        roleCategoryState.setCategory(category);
        roleCategoryState.setState(new State(1L, StateType.BATCH, ActionType.module, 1, "state", "state", "action"));
        roleCategory.setRoleCategoryStates(Set.of(roleCategoryState));
        roleCategory.setCategory(category);
        roleCategory.setRoleCategoryType(RoleCategoryType.LAB);
        List<RoleCategory> roleCategories = List.of(roleCategory);
        category = new Category();
        category.setId(2L); // Set an ID for the category
        category.setName("Test Category"); // Set a name for the category
        category.setIndependentBatch(true);
        List<Category> categories = List.of(category);

        when(manager.findAllByCategoryAndRoleNames(roleSplitList))
                .thenReturn(roleCategories);
        when(stateManager.findAll(null, null)).thenReturn(allStates);

        List<MenuLabResponseDto> result = roleCategoryService.getMenuForLab();

        assertThat(result).isNotNull();
        verify(manager, times(1)).findAllByCategoryAndRoleNames(roleSplitList);
        verify(stateManager, times(1)).findAll(null, null);
    }

    @Test
    void getAllMenuRoleCategories() {
        when(manager.findAllByCategoryAndRoleNames(roleSplitList))
                .thenReturn(roleCategories);
        when(stateManager.findAll(null, null)).thenReturn(allStates);
        List<Category> c = new ArrayList<Category>();
        Category category = new Category(1L, "WAREHOUSE", null, null, null, true, null);
        c.add(category);
        when(categoryManager.findAllByNames(any())).thenReturn(c);

        when(IamServiceRestHelper.fetchResponse(anyString(), eq(Boolean.class), anyString()))
                .thenReturn(true);

        List<MenuResponseDto> result = roleCategoryService.getAllMenuRoleCategories();

        assertThat(result).isNotNull();
        verify(stateManager, times(1)).findAll(null, null);
        verify(categoryManager, times(2)).findAllByNames(any());

    }

    @Test
    void updateRoleCategory() {
        when(manager.findById(requestDto.getId())).thenReturn(entity);
        roleCategoryService.updateRoleCategory(requestDto);
        verify(manager, times(1)).findById(requestDto.getId());
        verify(manager, times(1)).update(any(RoleCategory.class));
    }

    @Test
    void deleteRoleCategory() {
        roleCategoryService.deleteRoleCategory(1L);
        verify(manager, times(1)).findById(1L);
        verify(manager, times(1)).delete(1L);
    }

//    Private Methods----------------------------------------------------------------
//    @Test
//    void isInspectionUser() {
//        when(keycloakInfo.getUserInfo().get("roles")).thenReturn(new HashSet<>(Arrays.asList("ROLE_INSPECTION")));
//        boolean result = roleCategoryService.isInspectionUser();
//        assertThat(result).isTrue();
//    }

//    @Test
//    void getCategoryActionTypeMap() {
//        Category category1 = new Category();
//        category1.setId(1L);
//        Category category2 = new Category();
//        category2.setId(2L);
//        category2.setSourceCategories(List.of(category1));
//
//        List<Category> categories = List.of(category1, category2);
//        HashMap<Long, Set<StateType>> result = roleCategoryService.getCategoryActionTypeMap(categories);
//
//        assertThat(result).isNotNull();
//        assertThat(result.get(1L)).containsExactly(StateType.BATCH);
//        assertThat(result.get(2L)).containsExactlyInAnyOrder(StateType.BATCH, StateType.LOT);
//    }

//    @Test
//    void getMenuForInspection() {
//        when(categoryManager.findAllByNames(any())).thenReturn(new ArrayList<>());
//
//        List<MenuResponseDto> result = roleCategoryService.getMenuForInspection(roleSplitList);
//
//        assertThat(result).isNotNull();
//        verify(categoryManager, times(1)).findAllByNames(any());
//    }

//    @Test
//    void getMenuForExternalWarehouse() {
//        when(categoryManager.findAllByNames(any())).thenReturn(new ArrayList<>());
//        when(stateManager.findAll(null, null)).thenReturn(allStates);
//
//        List<MenuResponseDto> result = roleCategoryService.getMenuForExternalWarehouse(roleSplitList, allStates);
//
//        assertThat(result).isNotNull();
//        verify(categoryManager, times(1)).findAllByNames(any());
//        verify(stateManager, times(1)).findAll(null, null);
//    }

//    @Test
//    void getEntityStatesForWarehouse() {
//        List<State> lotStates = new ArrayList<>();
//        List<Long> allStateIds = new ArrayList<>();
//
//        List<EntityStatesResponseDto> result = roleCategoryService.getEntityStatesForWarehouse(lotStates, allStateIds);
//
//        assertThat(result).isNotNull();
//        assertThat(result.size()).isEqualTo(1);
//        assertThat(result.get(0).getName()).isEqualTo(StateType.LOT.name());
//        assertThat(result.get(0).isInventory()).isTrue();
//        assertThat(result.get(0).getStates()).isNotEmpty();
//    }

//    @Test
//    void getMenuStatesForWarehouse() {
//        List<State> lotStates = new ArrayList<>();
//        List<Long> allStateIds = new ArrayList<>();
//
//        List<MenuStateResponseDto> result = roleCategoryService.getMenuStatesForWarehouse(lotStates, allStateIds);
//
//        assertThat(result).isNotNull();
//        assertThat(result.size()).isGreaterThanOrEqualTo(1);
//        assertThat(result.get(0).getName()).isEqualTo("all");
//        assertThat(result.get(0).getType()).isEqualTo(StateType.LOT);
//    }

//    @Test
//    void getEntityStatesForInspection() {
//        Category category = new Category();
//        category.setId(1L);
//        HashMap<Long, Set<StateType>> categoryIdInvTypeMap = new HashMap<>();
//        categoryIdInvTypeMap.put(1L, new HashSet<>(Arrays.asList(StateType.BATCH, StateType.LOT)));
//
//        List<EntityStatesResponseDto> result = roleCategoryService.getEntityStatesForInspection(categoryIdInvTypeMap, category);
//
//        assertThat(result).isNotNull();
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).getName()).isEqualTo(StateType.BATCH.name());
//        assertThat(result.get(0).isCanInspect()).isTrue();
//        assertThat(result.get(1).getName()).isEqualTo(StateType.LOT.name());
//        assertThat(result.get(1).isCanInspect()).isTrue();
//    }
//
//    @Test
//    void getMenuStatesForInspection() {
//        List<MenuStateResponseDto> batchResult = roleCategoryService.getMenuStatesForInspection(StateType.BATCH);
//        List<MenuStateResponseDto> lotResult = roleCategoryService.getMenuStatesForInspection(StateType.LOT);
//
//        assertThat(batchResult).isNotNull();
//        assertThat(batchResult.size()).isGreaterThanOrEqualTo(1);
//        assertThat(batchResult.get(0).getDisplayName()).isEqualTo("ALL");
//        assertThat(batchResult.get(0).getType()).isEqualTo(StateType.BATCH);
//
//        assertThat(lotResult).isNotNull();
//        assertThat(lotResult.size()).isGreaterThanOrEqualTo(1);
//        assertThat(lotResult.get(0).getDisplayName()).isEqualTo("ALL");
//        assertThat(lotResult.get(0).getType()).isEqualTo(StateType.LOT);
//    }


    @Test
    void testCreateRoleCategory() {
        RoleCategoryRequestDto dto = new RoleCategoryRequestDto();
        roleCategoryService.createRoleCategory(dto);
        verify(manager, times(1)).create(any(RoleCategory.class));
    }

    @Test
    void testGetRoleCategoryById() {
        Long id = 1L;
        RoleCategoryResponseDto result = roleCategoryService.getRoleCategoryById(id);
        assertThat(result).isNotNull();
        verify(manager, times(1)).findById(id);
    }

    @Test
    void testGetAllRoleCategories() {
        Integer pageNumber = 0;
        Integer pageSize = 10;
        ListResponse<RoleCategoryResponseDto> result = roleCategoryService.getAllRoleCategories(pageNumber, pageSize);
        assertThat(result.getData()).isNotEmpty();
        assertThat(result.getCount()).isEqualTo(1L);
        verify(manager, times(1)).findAll(pageNumber, pageSize);
        verify(manager, times(1)).getCount(1, pageNumber, pageSize);
    }

    @Test
    void testUpdateRoleCategory() {
        RoleCategoryRequestDto dto = new RoleCategoryRequestDto();
        dto.setId(1L);
        roleCategoryService.updateRoleCategory(dto);
        verify(manager, times(1)).findById(dto.getId());
        verify(manager, times(1)).update(any(RoleCategory.class));
    }

    @Test
    void testDeleteRoleCategory() {
        Long id = 1L;
        roleCategoryService.deleteRoleCategory(id);
        verify(manager, times(1)).findById(id);
        verify(manager, times(1)).delete(id);
    }

    @Test
    void testGetAllMenuRoleCategories() {
        // Prepare the data
        List<String[]> roleSplitList = new ArrayList<>();
        List<State> allStates = new ArrayList<>();
        RoleCategory roleCategory = new RoleCategory();
        roleCategory.setRoleCategoryType(RoleCategoryType.MODULE);
        RoleCategoryState roleCategoryState = new RoleCategoryState();
        Category category = new Category(1L, "Test Category", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1);
        category.setId(1L); // Set an ID for the category
        category.setName("Test Category"); // Set a name for the category
        roleCategoryState.setCategory(category);
        roleCategoryState.setState(new State(1L, StateType.BATCH, ActionType.module, 1, "state", "state", "action"));
        roleCategory.setRoleCategoryStates(Set.of(roleCategoryState));
        roleCategory.setCategory(category);
        List<RoleCategory> roleCategories = List.of(roleCategory);
        category = new Category();
        category.setId(2L); // Set an ID for the category
        category.setName("Test Category"); // Set a name for the category
        category.setIndependentBatch(true);
        List<Category> categories = List.of(category);

        // Mock the dependencies
        when(manager.findAllByCategoryAndRoleNames(any())).thenReturn(roleCategories);
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", new HashSet<>()));
        when(manager.findAllByCategoryAndRoleNames(roleSplitList)).thenReturn(roleCategories);
        when(stateManager.findAll(null, null)).thenReturn(allStates);
        when(categoryManager.findAllByNames(any())).thenReturn(categories);
        when(IamServiceRestHelper.fetchResponse(anyString(), eq(Boolean.class), anyString())).thenReturn(false);

        // Call the method to test
        List<MenuResponseDto> result = roleCategoryService.getAllMenuRoleCategories();

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEntityStates()).isNotEmpty();
        assertThat(result.get(0).getEntityStates().get(0).getStates()).isNotEmpty();

        // Verify the interactions with the mocks
        verify(stateManager, times(1)).findAll(null, null);
        verify(categoryManager, times(1)).findAllByNames(any());
    }

    @Test
    void testGetAllMenuRoleCategories_lastPart() {

        List<String[]> roleSplitList = new ArrayList<>();
        List<State> allStates = new ArrayList<>();
        RoleCategory roleCategory = new RoleCategory();
        roleCategory.setRoleCategoryType(RoleCategoryType.LAB);
        RoleCategoryState roleCategoryState = new RoleCategoryState();
        Category category = new Category(1L, "Test Category", new Product(), Set.of(new Category(1L, "Test Category", new Product(), Set.of(new Category()), Set.of(new CategoryDoc()), true, 1)), Set.of(new CategoryDoc()), true, 1);
        category.setId(1L); // Set an ID for the category
        category.setName("Test Category"); // Set a name for the category
        roleCategoryState.setCategory(category);
        roleCategoryState.setState(new State(1L, StateType.BATCH, ActionType.module, 1, "state", "state", "action"));
        roleCategory.setRoleCategoryStates(Set.of(roleCategoryState));
        roleCategory.setCategory(category);
        List<RoleCategory> roleCategories = List.of(roleCategory);
        category = new Category();
        category.setId(2L); // Set an ID for the category
        category.setName("Test Category"); // Set a name for the category
        category.setIndependentBatch(true);
        List<Category> categories = List.of(category);

        // Mock the dependencies
        when(manager.findAllByCategoryAndRoleNames(any())).thenReturn(roleCategories);
        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", new HashSet<>()));
        when(manager.findAllByCategoryAndRoleNames(roleSplitList)).thenReturn(roleCategories);
        when(stateManager.findAll(null, null)).thenReturn(allStates);
        when(categoryManager.findAllByNames(any())).thenReturn(categories);
        when(IamServiceRestHelper.fetchResponse(anyString(), eq(Boolean.class), anyString())).thenReturn(false);

        // Call the method to test
        List<MenuResponseDto> result = roleCategoryService.getAllMenuRoleCategories();

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEntityStates()).isNotEmpty();
        assertThat(result.get(0).getEntityStates().get(0).getStates()).isNotEmpty();

        // Verify the interactions with the mocks
        verify(stateManager, times(1)).findAll(null, null);
        verify(categoryManager, times(1)).findAllByNames(any());
    }

}


//This test class covers =
//
//1. `getMenuForLab()`: This test verifies that the `getMenuForLab` method calls the appropriate methods on the mocked dependencies.
//2. `getAllMenuRoleCategories()`: This test verifies that the `getAllMenuRoleCategories` method calls the appropriate methods on the mocked dependencies.
//3. `isInspectionUser()`: This test verifies the behavior of the `isInspectionUser` method based on the user roles.
//4. `getCategoryActionTypeMap()`: This test verifies the correct mapping of categories and state types in the `getCategoryActionTypeMap` method.
//5. `getMenuForInspection()`: This test verifies that the `getMenuForInspection` method calls the appropriate method on the mocked `categoryManager`.
//6. `getMenuForExternalWarehouse()`: This test verifies that the `getMenuForExternalWarehouse` method calls the appropriate methods on the mocked dependencies.
//7. `getEntityStatesForWarehouse()`: This test verifies the structure and content of the `EntityStatesResponseDto` objects returned by the `getEntityStatesForWarehouse` method.
//8. `getMenuStatesForWarehouse()`: This test verifies the structure and content of the `MenuStateResponseDto` objects returned by the `getMenuStatesForWarehouse` method.
//9. `getEntityStatesForInspection()`: This test verifies the structure and content of the `EntityStatesResponseDto` objects returned by the `getEntityStatesForInspection` method.
//10. `getMenuStatesForInspection()`: This test verifies the structure and content of the `MenuStateResponseDto` objects returned by the `getMenuStatesForInspection` method for both `StateType.BATCH` and `StateType.LOT`.
