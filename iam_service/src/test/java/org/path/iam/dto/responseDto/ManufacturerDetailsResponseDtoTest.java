package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerDetailsResponseDtoTest {

    private ManufacturerDetailsResponseDto manufacturerDetailsResponseDto;

    @BeforeEach
    public void setUp() {
        manufacturerDetailsResponseDto = new ManufacturerDetailsResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerDetailsResponseDto dto = new ManufacturerDetailsResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getType());
        assertNull(dto.getAccreditedByAgency());
        assertNull(dto.getCompleteAddress());
        assertNull(dto.getLicenseNumber());
        assertNull(dto.getVendorType());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerDetailsResponseDto dto = new ManufacturerDetailsResponseDto(1L, "Test Name", "Test Type", "Test Agency", "Test Address", "Test License", "Test Vendor");
        assertEquals(1L, dto.getId());
        assertEquals("Test Name", dto.getName());
        assertEquals("Test Type", dto.getType());
        assertEquals("Test Agency", dto.getAccreditedByAgency());
        assertEquals("Test Address", dto.getCompleteAddress());
        assertEquals("Test License", dto.getLicenseNumber());
        assertEquals("Test Vendor", dto.getVendorType());
    }

    @Test
    public void testSettersAndGetters() {
        manufacturerDetailsResponseDto.setId(1L);
        manufacturerDetailsResponseDto.setName("Test Name");
        manufacturerDetailsResponseDto.setType("Test Type");
        manufacturerDetailsResponseDto.setAccreditedByAgency("Test Agency");
        manufacturerDetailsResponseDto.setCompleteAddress("Test Address");
        manufacturerDetailsResponseDto.setLicenseNumber("Test License");
        manufacturerDetailsResponseDto.setVendorType("Test Vendor");

        assertEquals(1L, manufacturerDetailsResponseDto.getId());
        assertEquals("Test Name", manufacturerDetailsResponseDto.getName());
        assertEquals("Test Type", manufacturerDetailsResponseDto.getType());
        assertEquals("Test Agency", manufacturerDetailsResponseDto.getAccreditedByAgency());
        assertEquals("Test Address", manufacturerDetailsResponseDto.getCompleteAddress());
        assertEquals("Test License", manufacturerDetailsResponseDto.getLicenseNumber());
        assertEquals("Test Vendor", manufacturerDetailsResponseDto.getVendorType());
    }
}
