package org.path.iam.manager;

import org.path.iam.dao.RoleDao;
import org.path.iam.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleManagerTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleManager roleManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByNames() {
        // Prepare test data
        List<String> roleNames = List.of("Admin", "User");
        List<Role> expectedRoles = new ArrayList<>(); // Add some dummy Role objects

        // Mock behavior of DAO
        when(roleDao.findByNames(roleNames)).thenReturn(expectedRoles);

        // Call method under test
        List<Role> actualRoles = roleManager.findByNames(roleNames);

        // Verify
        assertEquals(expectedRoles, actualRoles);
        verify(roleDao, times(1)).findByNames(roleNames);
    }

    @Test
    public void testFindByName() {
        // Prepare test data
        String roleName = "Admin";
        Role expectedRole = new Role(1L); // Create a dummy Role object

        // Mock behavior of DAO
        when(roleDao.findByName(roleName)).thenReturn(expectedRole);

        // Call method under test
        Role actualRole = roleManager.findByName(roleName);

        // Verify
        assertEquals(expectedRole, actualRole);
        verify(roleDao, times(1)).findByName(roleName);
    }

    // Write similar test methods for other methods in RoleManager
}
