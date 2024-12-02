package org.path.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AddressTest {

    private Address address;
    private Village village;
    private Manufacturer manufacturer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        village = new Village();
        village.setId(1L);
        village.setName("Test Village");

        manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        manufacturer.setName("Test Manufacturer");

        address = new Address();
        address.setId(1L);
        address.setLaneOne("Lane One");
        address.setLaneTwo("Lane Two");
        address.setVillage(village);
        address.setPinCode("123456");
        address.setLatitude(12.34);
        address.setLongitude(56.78);
        address.setManufacturer(manufacturer);
    }

    @Test
    public void testNoArgsConstructor() {
        Address newAddress = new Address();
        assertNull(newAddress.getId());
        assertNull(newAddress.getLaneOne());
        assertNull(newAddress.getLaneTwo());
        assertNull(newAddress.getVillage());
        assertNull(newAddress.getPinCode());
        assertNull(newAddress.getLatitude());
        assertNull(newAddress.getLongitude());
        assertNull(newAddress.getManufacturer());
    }

    @Test
    public void testAllArgsConstructor() {
        Address newAddress = new Address(2L, "Lane One", "Lane Two", village, "654321", 34.56, 78.90, manufacturer);
        assertEquals(2L, newAddress.getId());
        assertEquals("Lane One", newAddress.getLaneOne());
        assertEquals("Lane Two", newAddress.getLaneTwo());
        assertEquals(village, newAddress.getVillage());
        assertEquals("654321", newAddress.getPinCode());
        assertEquals(34.56, newAddress.getLatitude());
        assertEquals(78.90, newAddress.getLongitude());
        assertEquals(manufacturer, newAddress.getManufacturer());
    }

    @Test
    public void testSettersAndGetters() {
        assertEquals(1L, address.getId());
        assertEquals("Lane One", address.getLaneOne());
        assertEquals("Lane Two", address.getLaneTwo());
        assertEquals(village, address.getVillage());
        assertEquals("123456", address.getPinCode());
        assertEquals(12.34, address.getLatitude());
        assertEquals(56.78, address.getLongitude());
        assertEquals(manufacturer, address.getManufacturer());

        address.setId(2L);
        address.setLaneOne("New Lane One");
        address.setLaneTwo("New Lane Two");
        Village newVillage = new Village();
        newVillage.setId(2L);
        newVillage.setName("New Village");
        address.setVillage(newVillage);
        address.setPinCode("654321");
        address.setLatitude(34.56);
        address.setLongitude(78.90);
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setId(2L);
        newManufacturer.setName("New Manufacturer");
        address.setManufacturer(newManufacturer);

        assertEquals(2L, address.getId());
        assertEquals("New Lane One", address.getLaneOne());
        assertEquals("New Lane Two", address.getLaneTwo());
        assertEquals(newVillage, address.getVillage());
        assertEquals("654321", address.getPinCode());
        assertEquals(34.56, address.getLatitude());
        assertEquals(78.90, address.getLongitude());
        assertEquals(newManufacturer, address.getManufacturer());
    }


}
