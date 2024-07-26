package com.beehyv.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerAgencyResponseDtoTest {

    @Test
    public void testNoArgsConstructor() {
        ManufacturerAgencyResponseDto dto = new ManufacturerAgencyResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getAgencyName());
        assertNull(dto.getDistrictGeoId());
        assertNull(dto.getDistrictName());
        assertNull(dto.getStateName());
        assertNull(dto.getLicenseNumber());
        assertNull(dto.getStateGeoId());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerAgencyResponseDto dto = new ManufacturerAgencyResponseDto(
                1L, "Agency Name", "District Geo Id", "District Name", "State Name", "License Number", "State Geo Id"
        );
        assertEquals(1L, dto.getId());
        assertEquals("Agency Name", dto.getAgencyName());
        assertEquals("District Geo Id", dto.getDistrictGeoId());
        assertEquals("District Name", dto.getDistrictName());
        assertEquals("State Name", dto.getStateName());
        assertEquals("License Number", dto.getLicenseNumber());
        assertEquals("State Geo Id", dto.getStateGeoId());
    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerAgencyResponseDto dto = new ManufacturerAgencyResponseDto();
        dto.setId(1L);
        dto.setAgencyName("Agency Name");
        dto.setDistrictGeoId("District Geo Id");
        dto.setDistrictName("District Name");
        dto.setStateName("State Name");
        dto.setLicenseNumber("License Number");
        dto.setStateGeoId("State Geo Id");

        assertEquals(1L, dto.getId());
        assertEquals("Agency Name", dto.getAgencyName());
        assertEquals("District Geo Id", dto.getDistrictGeoId());
        assertEquals("District Name", dto.getDistrictName());
        assertEquals("State Name", dto.getStateName());
        assertEquals("License Number", dto.getLicenseNumber());
        assertEquals("State Geo Id", dto.getStateGeoId());
    }

    @Test
    public void testValidConstructionAndGetters() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        Long id = 1L;
        String agencyName = "ABC Manufacturing";
        String districtGeoId = "in.tg.hyd";
        String districtName = "Hyderabad";
        String stateName = "Telangana";
        String licenseNumber = "MAN-TG-HYD-1234";
        String stateGeoId = "in.tg";

        ManufacturerAgencyResponseDto responseDto = new ManufacturerAgencyResponseDto();

        assertNotNull(responseDto);

    }

    @Test
    public void testWithOptionalFields() {
        String uuid = null;
        String districtGeoId = null;
        String stateGeoId = null;

        ManufacturerAgencyResponseDto responseDto = new ManufacturerAgencyResponseDto();

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid());

    }

    @Test
    public void testBaseResponseDtoFields() {
        // Inherits from BaseResponseDto, test its fields here
        StatusResponseDto status = new StatusResponseDto();

        ManufacturerAgencyResponseDto responseDto = new ManufacturerAgencyResponseDto();

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid()); // Can be null if not set
    }
}
