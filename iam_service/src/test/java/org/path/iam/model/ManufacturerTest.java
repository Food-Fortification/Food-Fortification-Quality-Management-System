package org.path.iam.model;

import org.path.iam.enums.VendorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerTest {

    private Manufacturer manufacturer;

    @BeforeEach
    void setUp() {
        manufacturer = new Manufacturer();
    }

    @Test
    void testManufacturerInitialization() {
        // Assert initial state of manufacturer object
        assertNull(manufacturer.getId());
        assertNull(manufacturer.getName());
        assertNull(manufacturer.getType());
        assertNull(manufacturer.getAccreditedByAgency());
        assertNull(manufacturer.getVendorType());
        assertNull(manufacturer.getAgencyName());
        assertNull(manufacturer.getLicenseNumber());
        assertNull(manufacturer.getTotalScore());
        assertNotNull(manufacturer.getIsRawMaterialsProcured());
        assertFalse(manufacturer.getIsRawMaterialsProcured());
        assertNull(manufacturer.getExternalManufacturerId());
        assertNotNull(manufacturer.getManufacturerDocs());
        assertTrue(manufacturer.getManufacturerDocs().isEmpty());
        assertNotNull(manufacturer.getManufacturerCategories());
        assertTrue(manufacturer.getManufacturerCategories().isEmpty());
        assertNull(manufacturer.getAddress());
        assertNull(manufacturer.getLicenseStatus());
        assertNotNull(manufacturer.getUsers());
        assertTrue(manufacturer.getUsers().isEmpty());

    }

    @Test
    void testManufacturerConstructorWithArgs() {
        // Arrange
        Long id = 1L;

        // Act
        Manufacturer manufacturer = new Manufacturer(id);

        // Assert
        assertEquals(id, manufacturer.getId());

    }

    @Test
    void testIdSetterGetter() {
        // Arrange
        Long id = 1L;

        // Act
        manufacturer.setId(id);

        // Assert
        assertEquals(id, manufacturer.getId());
    }

    @Test
    void testNameSetterGetter() {
        // Arrange
        String name = "Test Manufacturer";

        // Act
        manufacturer.setName(name);

        // Assert
        assertEquals(name, manufacturer.getName());
    }

    @Test
    void testTypeSetterGetter() {
        // Arrange
        String type = "Test Type";

        // Act
        manufacturer.setType(type);

        // Assert
        assertEquals(type, manufacturer.getType());
    }

    @Test
    void testAccreditedByAgencySetterGetter() {
        // Arrange
        Boolean accreditedByAgency = true;

        // Act
        manufacturer.setAccreditedByAgency(accreditedByAgency);

        // Assert
        assertEquals(accreditedByAgency, manufacturer.getAccreditedByAgency());
    }

    @Test
    void testVendorTypeSetterGetter() {
        // Arrange
        VendorType vendorType = VendorType.Manufacturer;

        // Act
        manufacturer.setVendorType(vendorType);

        // Assert
        assertEquals(vendorType, manufacturer.getVendorType());
    }

    @Test
    void testAgencyNameSetterGetter() {
        // Arrange
        String agencyName = "Test Agency";

        // Act
        manufacturer.setAgencyName(agencyName);

        // Assert
        assertEquals(agencyName, manufacturer.getAgencyName());
    }

    @Test
    void testLicenseNumberSetterGetter() {
        // Arrange
        String licenseNumber = "123456";

        // Act
        manufacturer.setLicenseNumber(licenseNumber);

        // Assert
        assertEquals(licenseNumber, manufacturer.getLicenseNumber());
    }

    @Test
    void testTotalScoreSetterGetter() {
        // Arrange
        Double totalScore = 85.5;

        // Act
        manufacturer.setTotalScore(totalScore);

        // Assert
        assertEquals(totalScore, manufacturer.getTotalScore());
    }

    @Test
    void testIsRawMaterialsProcuredSetterGetter() {
        // Arrange
        Boolean isRawMaterialsProcured = true;

        // Act
        manufacturer.setIsRawMaterialsProcured(isRawMaterialsProcured);

        // Assert
        assertEquals(isRawMaterialsProcured, manufacturer.getIsRawMaterialsProcured());
    }

    @Test
    void testExternalManufacturerIdSetterGetter() {
        // Arrange
        String externalManufacturerId = "EXT123";

        // Act
        manufacturer.setExternalManufacturerId(externalManufacturerId);

        // Assert
        assertEquals(externalManufacturerId, manufacturer.getExternalManufacturerId());
    }

    @Test
    void testManufacturerDocsSetterGetter() {
        // Arrange
        ManufacturerDoc doc1 = new ManufacturerDoc();
        ManufacturerDoc doc2 = new ManufacturerDoc();
        Set<ManufacturerDoc> docs = new HashSet<>();
        docs.add(doc1);
        docs.add(doc2);

        // Act
        manufacturer.setManufacturerDocs(docs);

        // Assert
        assertEquals(2, manufacturer.getManufacturerDocs().size());
        assertTrue(manufacturer.getManufacturerDocs().contains(doc1));
        assertTrue(manufacturer.getManufacturerDocs().contains(doc2));
    }

    @Test
    void testAddManufacturerDoc() {
        // Arrange
        ManufacturerDoc doc = new ManufacturerDoc();

        // Act
        manufacturer.getManufacturerDocs().add(doc);

        // Assert
        assertEquals(1, manufacturer.getManufacturerDocs().size());
        assertTrue(manufacturer.getManufacturerDocs().contains(doc));
    }

    @Test
    void testRemoveManufacturerDoc() {
        // Arrange
        ManufacturerDoc doc = new ManufacturerDoc();
        manufacturer.getManufacturerDocs().add(doc);

        // Act
        manufacturer.getManufacturerDocs().remove(doc);

        // Assert
        assertTrue(manufacturer.getManufacturerDocs().isEmpty());
    }

    @Test
    void testManufacturerCategoriesSetterGetter() {
        // Arrange
        ManufacturerCategory category1 = new ManufacturerCategory();
        ManufacturerCategory category2 = new ManufacturerCategory();
        Set<ManufacturerCategory> categories = new HashSet<>();
        categories.add(category1);
        categories.add(category2);

        // Act
        manufacturer.setManufacturerCategories(categories);

        // Assert
        assertEquals(2, manufacturer.getManufacturerCategories().size());
        assertTrue(manufacturer.getManufacturerCategories().contains(category1));
        assertTrue(manufacturer.getManufacturerCategories().contains(category2));
    }

    @Test
    void testAddManufacturerCategory() {
        // Arrange
        ManufacturerCategory category = new ManufacturerCategory();

        // Act
        manufacturer.getManufacturerCategories().add(category);

        // Assert
        assertEquals(1, manufacturer.getManufacturerCategories().size());
        assertTrue(manufacturer.getManufacturerCategories().contains(category));
    }

    @Test
    void testRemoveManufacturerCategory() {
        // Arrange
        ManufacturerCategory category = new ManufacturerCategory();
        manufacturer.getManufacturerCategories().add(category);

        // Act
        manufacturer.getManufacturerCategories().remove(category);

        // Assert
        assertTrue(manufacturer.getManufacturerCategories().isEmpty());
    }

    @Test
    void testAddressSetterGetter() {
        // Arrange
        Address address = new Address();

        // Act
        manufacturer.setAddress(address);

        // Assert
        assertEquals(address, manufacturer.getAddress());
    }

    @Test
    void testLicenseStatusSetterGetter() {
        // Arrange
        Boolean licenseStatus = true;

        // Act
        manufacturer.setLicenseStatus(licenseStatus);

        // Assert
        assertEquals(licenseStatus, manufacturer.getLicenseStatus());
    }

    @Test
    void testUsersSetterGetter() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);

        // Act
        manufacturer.setUsers(users);

        // Assert
        assertEquals(2, manufacturer.getUsers().size());
        assertTrue(manufacturer.getUsers().contains(user1));
        assertTrue(manufacturer.getUsers().contains(user2));
    }

    @Test
    void testAddUser() {
        // Arrange
        User user = new User();

        // Act
        manufacturer.getUsers().add(user);

        // Assert
        assertEquals(1, manufacturer.getUsers().size());
        assertTrue(manufacturer.getUsers().contains(user));
    }

    @Test
    void testRemoveUser() {
        // Arrange
        User user = new User();
        manufacturer.getUsers().add(user);

        // Act
        manufacturer.getUsers().remove(user);

        // Assert
        assertTrue(manufacturer.getUsers().isEmpty());
    }
}