package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.RoleCategoryResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.helper.IamServiceRestHelper;
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

}


