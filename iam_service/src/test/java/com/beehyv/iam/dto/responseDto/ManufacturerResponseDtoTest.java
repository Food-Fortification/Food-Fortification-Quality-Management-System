package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.iam.enums.VendorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerResponseDtoTest {

    private ManufacturerResponseDto manufacturerResponseDto;

    @BeforeEach
    public void setUp() {
        manufacturerResponseDto = new ManufacturerResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerResponseDto dto = new ManufacturerResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getCompleteAddress());
        assertNull(dto.getType());
        assertNull(dto.getAccreditedByAgency());
        assertNull(dto.getVendorType());
        assertNull(dto.getAgencyName());
        assertNull(dto.getLicenseNumber());
        assertNull(dto.getTotalScore());
        assertNull(dto.getExternalManufacturerId());
        assertNull(dto.getManufacturerDocs());
        assertNull(dto.getTargetManufacturers());
        assertNull(dto.getAddress());
        assertNull(dto.getManufacturerType());
        assertNull(dto.getManufacturerCategory());
        assertNull(dto.getManufacturerAttributes());
        assertNull(dto.getManufacturerProperties());
    }

    @Test
    public void testAllArgsConstructor() {
        Set<ManufacturerDocsResponseDto> manufacturerDocs = new HashSet<>();
        Set<ManufacturerResponseDto> targetManufacturers = new HashSet<>();
        AddressResponseDto address = new AddressResponseDto();
        Set<ManufacturerCategoryResponseDto> manufacturerCategory = new HashSet<>();
        Set<ManufacturerCategoryAttributesResponseDto> manufacturerAttributes = new HashSet<>();
        Set<ManufacturerPropertyResponseDto> manufacturerProperties = new HashSet<>();

        ManufacturerResponseDto dto = new ManufacturerResponseDto(1L, "Manufacturer Name", "Complete Address", "Type", true,
                VendorType.Manufacturer, "Agency Name", "License Number", 100.0, "External ID", manufacturerDocs, targetManufacturers,
                address, ManufacturerType.PRIVATE, manufacturerCategory, manufacturerAttributes, manufacturerProperties);

        assertEquals(1L, dto.getId());
        assertEquals("Manufacturer Name", dto.getName());
        assertEquals("Complete Address", dto.getCompleteAddress());
        assertEquals("Type", dto.getType());
        assertEquals(true, dto.getAccreditedByAgency());
        assertEquals("Agency Name", dto.getAgencyName());
        assertEquals("License Number", dto.getLicenseNumber());
        assertEquals(100.0, dto.getTotalScore());
        assertEquals("External ID", dto.getExternalManufacturerId());
        assertEquals(manufacturerDocs, dto.getManufacturerDocs());
        assertEquals(targetManufacturers, dto.getTargetManufacturers());
        assertEquals(address, dto.getAddress());
        assertEquals(manufacturerCategory, dto.getManufacturerCategory());
        assertEquals(manufacturerAttributes, dto.getManufacturerAttributes());
        assertEquals(manufacturerProperties, dto.getManufacturerProperties());
    }

    // Write additional tests for setters and getters if necessary
}
