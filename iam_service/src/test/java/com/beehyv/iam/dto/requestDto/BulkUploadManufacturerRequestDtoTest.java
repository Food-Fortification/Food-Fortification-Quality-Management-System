package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.dto.responseDto.AddressResponseDto;
import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.iam.enums.VendorType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BulkUploadManufacturerRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        BulkUploadManufacturerRequestDto dto = new BulkUploadManufacturerRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getLicenseNumber()).isNull();
        assertThat(dto.getType()).isNull();
        assertThat(dto.getAccreditedByAgency()).isNull();
        assertThat(dto.getVendorType()).isNull();
        assertThat(dto.getAgencyName()).isNull();
        assertThat(dto.getPremix()).isNull();
        assertThat(dto.getFrk()).isNull();
        assertThat(dto.getMiller()).isNull();
        assertThat(dto.getManufacturerType()).isNull();
        assertThat(dto.getLaneOne()).isNull();
        assertThat(dto.getLaneTwo()).isNull();
        assertThat(dto.getPinCode()).isNull();
        assertThat(dto.getLatitude()).isNull();
        assertThat(dto.getLongitude()).isNull();
        assertThat(dto.getVillageName()).isNull();
        assertThat(dto.getCode()).isNull();
        assertThat(dto.getGeoId()).isNull();
        assertThat(dto.getDistrictName()).isNull();
        assertThat(dto.getStateName()).isNull();
        assertThat(dto.getCountryName()).isNull();
        assertThat(dto.getAddress()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        AddressResponseDto address = new AddressResponseDto();
        BulkUploadManufacturerRequestDto dto = new BulkUploadManufacturerRequestDto(
                "Test Name", "License123", "Type1", "Agency1", VendorType.Manufacturer, "Agency Name",
                "Premix1", "FRK1", "Miller1", ManufacturerType.PRIVATE, "Lane1", "Lane2",
                "123456", 12.34, 56.78, "Village1", "Code1", "GeoId1", "District1", "State1", "Country1",
                address
        );

        assertThat(dto.getName()).isEqualTo("Test Name");
        assertThat(dto.getLicenseNumber()).isEqualTo("License123");
        assertThat(dto.getType()).isEqualTo("Type1");
        assertThat(dto.getAccreditedByAgency()).isEqualTo("Agency1");
        assertThat(dto.getVendorType()).isEqualTo(VendorType.Manufacturer);
        assertThat(dto.getAgencyName()).isEqualTo("Agency Name");
        assertThat(dto.getPremix()).isEqualTo("Premix1");
        assertThat(dto.getFrk()).isEqualTo("FRK1");
        assertThat(dto.getMiller()).isEqualTo("Miller1");
        assertThat(dto.getManufacturerType()).isEqualTo(ManufacturerType.PRIVATE);
        assertThat(dto.getLaneOne()).isEqualTo("Lane1");
        assertThat(dto.getLaneTwo()).isEqualTo("Lane2");
        assertThat(dto.getPinCode()).isEqualTo("123456");
        assertThat(dto.getLatitude()).isEqualTo(12.34);
        assertThat(dto.getLongitude()).isEqualTo(56.78);
        assertThat(dto.getVillageName()).isEqualTo("Village1");
        assertThat(dto.getCode()).isEqualTo("Code1");
        assertThat(dto.getGeoId()).isEqualTo("GeoId1");
        assertThat(dto.getDistrictName()).isEqualTo("District1");
        assertThat(dto.getStateName()).isEqualTo("State1");
        assertThat(dto.getCountryName()).isEqualTo("Country1");
        assertThat(dto.getAddress()).isEqualTo(address);
    }

    @Test
    void testSettersAndGetters() {
        BulkUploadManufacturerRequestDto dto = new BulkUploadManufacturerRequestDto();
        AddressResponseDto address = new AddressResponseDto();

        dto.setName("Updated Name");
        dto.setLicenseNumber("Updated License");
        dto.setType("Updated Type");
        dto.setAccreditedByAgency("Updated Agency");
        dto.setVendorType(VendorType.Manufacturer);
        dto.setAgencyName("Updated Agency Name");
        dto.setPremix("Updated Premix");
        dto.setFrk("Updated FRK");
        dto.setMiller("Updated Miller");
        dto.setManufacturerType(ManufacturerType.PRIVATE);
        dto.setLaneOne("Updated Lane1");
        dto.setLaneTwo("Updated Lane2");
        dto.setPinCode("654321");
        dto.setLatitude(21.43);
        dto.setLongitude(65.87);
        dto.setVillageName("Updated Village");
        dto.setCode("Updated Code");
        dto.setGeoId("Updated GeoId");
        dto.setDistrictName("Updated District");
        dto.setStateName("Updated State");
        dto.setCountryName("Updated Country");
        dto.setAddress(address);

        assertThat(dto.getName()).isEqualTo("Updated Name");
        assertThat(dto.getLicenseNumber()).isEqualTo("Updated License");
        assertThat(dto.getType()).isEqualTo("Updated Type");
        assertThat(dto.getAccreditedByAgency()).isEqualTo("Updated Agency");
        assertThat(dto.getVendorType()).isEqualTo(VendorType.Manufacturer);
        assertThat(dto.getAgencyName()).isEqualTo("Updated Agency Name");
        assertThat(dto.getPremix()).isEqualTo("Updated Premix");
        assertThat(dto.getFrk()).isEqualTo("Updated FRK");
        assertThat(dto.getMiller()).isEqualTo("Updated Miller");
        assertThat(dto.getLaneOne()).isEqualTo("Updated Lane1");
        assertThat(dto.getLaneTwo()).isEqualTo("Updated Lane2");
        assertThat(dto.getPinCode()).isEqualTo("654321");
        assertThat(dto.getLatitude()).isEqualTo(21.43);
        assertThat(dto.getLongitude()).isEqualTo(65.87);
        assertThat(dto.getVillageName()).isEqualTo("Updated Village");
        assertThat(dto.getCode()).isEqualTo("Updated Code");
        assertThat(dto.getGeoId()).isEqualTo("Updated GeoId");
        assertThat(dto.getDistrictName()).isEqualTo("Updated District");
        assertThat(dto.getStateName()).isEqualTo("Updated State");
        assertThat(dto.getCountryName()).isEqualTo("Updated Country");
        assertThat(dto.getAddress()).isEqualTo(address);
    }

    @Test
    void testToString() {
        AddressResponseDto address = new AddressResponseDto();
        BulkUploadManufacturerRequestDto dto = new BulkUploadManufacturerRequestDto(
                "Test Name", "License123", "Type1", "Agency1", VendorType.Manufacturer, "Agency Name",
                "Premix1", "FRK1", "Miller1", ManufacturerType.MATERIAL, "Lane1", "Lane2",
                "123456", 12.34, 56.78, "Village1", "Code1", "GeoId1", "District1", "State1", "Country1",
                address
        );

        String dtoString = dto.toString();


    }
}