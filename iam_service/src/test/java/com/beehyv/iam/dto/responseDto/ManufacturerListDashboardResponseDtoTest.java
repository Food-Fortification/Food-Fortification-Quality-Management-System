package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.VendorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerListDashboardResponseDtoTest {

    private ManufacturerListDashboardResponseDto manufacturerListDashboardResponseDto;

    @BeforeEach
    public void setUp() {
        manufacturerListDashboardResponseDto = new ManufacturerListDashboardResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerListDashboardResponseDto dto = new ManufacturerListDashboardResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getCompleteAddress());
        assertNull(dto.getType());
        assertNull(dto.getAccreditedByAgency());
        assertNull(dto.getVendorType());
        assertNull(dto.getAgencyName());
        assertNull(dto.getLicenseNumber());
        assertNull(dto.getLaneOne());
        assertNull(dto.getLaneTwo());
        assertNull(dto.getVillageName());
        assertNull(dto.getDistrictName());
        assertNull(dto.getDistrictGeoId());
        assertNull(dto.getStateName());
        assertNull(dto.getCountryName());
        assertNull(dto.getPinCode());
        assertNull(dto.getLatitude());
        assertNull(dto.getLongitude());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerListDashboardResponseDto dto = new ManufacturerListDashboardResponseDto(
                1L, "Manufacturer Name", "Complete Address", "Type", true, VendorType.Manufacturer, "Agency Name",
                "License Number", "Lane One", "Lane Two", "Village Name", "District Name", "District GeoId",
                "State Name", "Country Name", "Pin Code", "Latitude", "Longitude");

        assertEquals(1L, dto.getId());
        assertEquals("Manufacturer Name", dto.getName());
        assertEquals("Complete Address", dto.getCompleteAddress());
        assertEquals("Type", dto.getType());
        assertEquals(true, dto.getAccreditedByAgency());
        assertEquals(VendorType.Manufacturer, dto.getVendorType());
        assertEquals("Agency Name", dto.getAgencyName());
        assertEquals("License Number", dto.getLicenseNumber());
        assertEquals("Lane One", dto.getLaneOne());
        assertEquals("Lane Two", dto.getLaneTwo());
        assertEquals("Village Name", dto.getVillageName());
        assertEquals("District Name", dto.getDistrictName());
        assertEquals("District GeoId", dto.getDistrictGeoId());
        assertEquals("State Name", dto.getStateName());
        assertEquals("Country Name", dto.getCountryName());
        assertEquals("Pin Code", dto.getPinCode());
        assertEquals("Latitude", dto.getLatitude());
        assertEquals("Longitude", dto.getLongitude());
    }

    @Test
    public void testSettersAndGetters() {
        manufacturerListDashboardResponseDto.setId(1L);
        manufacturerListDashboardResponseDto.setName("Manufacturer Name");
        manufacturerListDashboardResponseDto.setCompleteAddress("Complete Address");
        manufacturerListDashboardResponseDto.setType("Type");
        manufacturerListDashboardResponseDto.setAccreditedByAgency(true);
        manufacturerListDashboardResponseDto.setVendorType(VendorType.Manufacturer);
        manufacturerListDashboardResponseDto.setAgencyName("Agency Name");
        manufacturerListDashboardResponseDto.setLicenseNumber("License Number");
        manufacturerListDashboardResponseDto.setLaneOne("Lane One");
        manufacturerListDashboardResponseDto.setLaneTwo("Lane Two");
        manufacturerListDashboardResponseDto.setVillageName("Village Name");
        manufacturerListDashboardResponseDto.setDistrictName("District Name");
        manufacturerListDashboardResponseDto.setDistrictGeoId("District GeoId");
        manufacturerListDashboardResponseDto.setStateName("State Name");
        manufacturerListDashboardResponseDto.setCountryName("Country Name");
        manufacturerListDashboardResponseDto.setPinCode("Pin Code");
        manufacturerListDashboardResponseDto.setLatitude("Latitude");
        manufacturerListDashboardResponseDto.setLongitude("Longitude");

        assertEquals(1L, manufacturerListDashboardResponseDto.getId());
        assertEquals("Manufacturer Name", manufacturerListDashboardResponseDto.getName());
        assertEquals("Complete Address", manufacturerListDashboardResponseDto.getCompleteAddress());
        assertEquals("Type", manufacturerListDashboardResponseDto.getType());
        assertEquals(true, manufacturerListDashboardResponseDto.getAccreditedByAgency());
        assertEquals("Agency Name", manufacturerListDashboardResponseDto.getAgencyName());
        assertEquals("License Number", manufacturerListDashboardResponseDto.getLicenseNumber());
        assertEquals("Lane One", manufacturerListDashboardResponseDto.getLaneOne());
        assertEquals("Lane Two", manufacturerListDashboardResponseDto.getLaneTwo());
        assertEquals("Village Name", manufacturerListDashboardResponseDto.getVillageName());
        assertEquals("District Name", manufacturerListDashboardResponseDto.getDistrictName());
        assertEquals("District GeoId", manufacturerListDashboardResponseDto.getDistrictGeoId());
        assertEquals("State Name", manufacturerListDashboardResponseDto.getStateName());
        assertEquals("Country Name", manufacturerListDashboardResponseDto.getCountryName());
        assertEquals("Pin Code", manufacturerListDashboardResponseDto.getPinCode());
        assertEquals("Latitude", manufacturerListDashboardResponseDto.getLatitude());
        assertEquals("Longitude", manufacturerListDashboardResponseDto.getLongitude());
    }
}
