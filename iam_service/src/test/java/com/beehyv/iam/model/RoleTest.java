package com.beehyv.iam.model;

import com.beehyv.iam.dao.RoleDao;
import com.beehyv.iam.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Mock
    private RoleDao roleRepository;
    @InjectMocks
    private RoleService roleService;

    @Test
    void testEqualsAndHashCode_SameIdAndName() {
        Role role1 = new Role(1L);
        role1.setName("Admin");
        Role role2 = new Role(1L);
        role2.setName("Admin");
    }

    @Test
    void testEqualsAndHashCode_DifferentId() {
        Role role1 = new Role(1L);
        role1.setName("Admin");
        Role role2 = new Role(2L);
        role2.setName("Admin");
        assertFalse(role1.equals(role2) || role2.equals(role1));
        assertNotEquals(role1.hashCode(), role2.hashCode());
    }

    // ToString Test
    @Test
    void testToString() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Admin");
        String expectedToString = "Role(id=1, name=Admin)";
    }


    // Equals and HashCode Test
    @Test
    void testEqualsAndHashCode_SameObjects() {
        Role role1 = new Role(1L);
        role1.setName("Admin");

        Role role2 = new Role(1L);
        role2.setName("Admin");

    }

    @Test
    void testEqualsAndHashCode_DifferentObjects() {
        Role role1 = new Role(1L);
        role1.setName("Admin");

        Role role2 = new Role(2L);
        role2.setName("Admin");

        assertNotEquals(role1, role2);
        assertNotEquals(role1.hashCode(), role2.hashCode());
    }


    @Test
    void testBoundaryValues_MaxId() {
        Role role = new Role(Long.MAX_VALUE);
        role.setName("Admin");
        assertEquals(Long.MAX_VALUE, role.getId());
    }

    @Test
    void testBoundaryValues_MaxLengthName() {
        Role role = new Role();
        String maxName = "A".repeat(255); // Max length allowed
        role.setName(maxName);
        assertEquals(maxName, role.getName());
    }


}
