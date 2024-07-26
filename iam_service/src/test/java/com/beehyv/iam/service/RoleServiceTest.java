package com.beehyv.iam.service;

import com.beehyv.iam.config.KeycloakCustomConfig;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.RoleResponseDto;
import com.beehyv.iam.manager.*;
import com.beehyv.iam.model.LabUsers;
import com.beehyv.iam.model.Role;
import com.beehyv.iam.model.User;
import com.beehyv.iam.model.UserRoleCategory;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    @Mock
    private RoleManager roleManager;

    @Mock
    private KeycloakCustomConfig keycloakCustomConfig;

    @Mock
    private UserManager userManager;

    @Mock
    private UserRoleCategoryManager userRoleCategoryManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private StatusManager statusManager;

    @Mock
    private LabUsersManager labUsersManager;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

 /*   @Test
    void testAssignRole_WithUserAndRoles() {
        // Prepare test data
        User user = new User();
        user.setId(1L);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        user.setManufacturer(manufacturer);
        LabUsers labUsers = new LabUsers();
        labUsers.setLabId(1L);
        user.setLabUsers(labUsers);

        RoleRequestDto roleRequestDto = new RoleRequestDto();
        roleRequestDto.setRoleName("ADMIN");
        roleRequestDto.setRoleCategoryType("LAB");
        List<RoleRequestDto> rolesMap = Collections.singletonList(roleRequestDto);

        // Mock behavior
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", Set.of("SUPERADMIN"));
        userInfo.put("manufacturerId", 1L);
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(userRoleCategoryManager.create(any())).thenReturn(new UserRoleCategory());
        when(roleManager.findByNames(any())).thenReturn(Collections.singletonList(new Role()));

        // Call the method
        List<Long> response = roleService.assignRole(user, rolesMap, false);

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.size());
    }*/
/*
    @Test
    void testAssignRole_WithAssignRoleRequestDto() {
        // Prepare test data
        AssignRoleRequestDto dto = new AssignRoleRequestDto();
        dto.setUserName("testUser");
        RoleRequestDto roleRequestDto = new RoleRequestDto();
        roleRequestDto.setRoleName("ADMIN");
        roleRequestDto.setRoleCategoryType("LAB");
        dto.setRoles(Collections.singletonList(roleRequestDto));

        // Mock behavior
        User user = new User();
        when(userManager.findByName(dto.getUserName())).thenReturn(user);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("roles", Set.of("SUPERADMIN"), "manufacturerId", 1L));
        when(userRoleCategoryManager.create(any())).thenReturn(new UserRoleCategory());
        when(roleManager.findByNames(any())).thenReturn(Collections.singletonList(new Role()));

        // Call the method
        List<Long> response = roleService.assignRole(dto);

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.size());
    }*/

    @Test
    void testAssignLabRole() {
        // Prepare test data
        Long labId = 1L;
        String roleCategory = "CATEGORY";
        Long categoryId = 1L;
        LabUsers labUser = new LabUsers();
        labUser.setId(1L);
        User user = new User();
        user.setRoleCategories(new HashSet<>(Collections.singletonList(new UserRoleCategory())));
        when(labUsersManager.findUsersByLabId(labId)).thenReturn(Collections.singletonList(labUser));
        when(userManager.findByLabUserId(labUser.getId())).thenReturn(user);
        when(userRoleCategoryManager.create(any())).thenReturn(new UserRoleCategory());

        // Call the method
    }
/*
    @Test
    void testRemoveRole() {
        // Prepare test data
        RemoveRoleRequestDto dto = new RemoveRoleRequestDto("rr",1L);
        dto.setUserName("testUser");
        dto.setRoleCategoryId(1L);
        User user = new User();
        UserRoleCategory roleCategory = new UserRoleCategory();
        Role role = new Role();
        role.setName("ROLE");
        roleCategory.setRole(role);
        roleCategory.setCategory("CATEGORY");
        roleCategory.setRoleCategoryType("LAB");
        roleCategory.setId(1L);
        roleCategory.setUser(user);
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("roles", Set.of("SUPERADMIN"), "manufacturerId", 1L));
        when(userManager.findByName(dto.getUserName())).thenReturn(user);
        when(userRoleCategoryManager.findById(dto.getRoleCategoryId())).thenReturn(roleCategory);
        doNothing().when(userRoleCategoryManager).delete(dto.getRoleCategoryId());
        when(keycloakCustomConfig.getInstance()).thenReturn((Keycloak) mock(RealmResource.class));

        // Call the method
        assertDoesNotThrow(() -> roleService.removeRole(dto));
    }*/

    @Test
    void testGetRoles() {
        // Prepare test data
        Integer pageNumber = 1;
        Integer pageSize = 10;
        Role role = new Role();
        role.setName("ADMIN");
        List<Role> roles = Collections.singletonList(role);
        when(roleManager.findAll(pageNumber, pageSize)).thenReturn(roles);
        when(roleManager.getCount(roles.size(), pageNumber, pageSize)).thenReturn(1L);

        // Call the method
        ListResponse<RoleResponseDto> response = roleService.getRoles(pageNumber, pageSize);

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.getData().size());
    }
}
