package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.iam.enums.VendorType;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FssaiManufacturerRequestDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testNoArgsConstructor() {
        FssaiManufacturerRequestDto dto = new FssaiManufacturerRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getCategoryNames()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getType()).isNull();
        assertThat(dto.getAccreditedByAgency()).isNull();
        assertThat(dto.getVendorType()).isNull();
        assertThat(dto.getAgencyName()).isNull();
        assertThat(dto.getLicenseNumber()).isNull();
        assertThat(dto.getManufacturerType()).isNull();
        assertThat(dto.getLaneOne()).isNull();
        assertThat(dto.getLaneTwo()).isNull();
        assertThat(dto.getVillageName()).isNull();
        assertThat(dto.getPinCode()).isNull();
        assertThat(dto.getLongitude()).isNull();
        assertThat(dto.getLatitude()).isNull();
        assertThat(dto.getDistrictName()).isNull();
        assertThat(dto.getStateName()).isNull();
        assertThat(dto.getCountryName()).isNull();
        assertThat(dto.getUserName()).isNull();
        assertThat(dto.getEmailAddress()).isNull();
        assertThat(dto.getFirstName()).isNull();
        assertThat(dto.getLastName()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        List<String> categories = List.of("Category1", "Category2");
        FssaiManufacturerRequestDto dto = new FssaiManufacturerRequestDto(categories, "Manufacturer1", "Type1", true,
                VendorType.Manufacturer, "Agency1", "License123", ManufacturerType.PRIVATE, "Lane1", "Lane2",
                "Village1", "123456", 12.34, 56.78, "District1", "State1", "Country1", "User1", "user@example.com",
                "John", "Doe");

        assertThat(dto.getCategoryNames()).isEqualTo(categories);
        assertThat(dto.getName()).isEqualTo("Manufacturer1");
        assertThat(dto.getType()).isEqualTo("Type1");
        assertThat(dto.getAccreditedByAgency()).isTrue();
        assertThat(dto.getVendorType()).isEqualTo(VendorType.Manufacturer);
        assertThat(dto.getAgencyName()).isEqualTo("Agency1");
        assertThat(dto.getLicenseNumber()).isEqualTo("License123");
        assertThat(dto.getManufacturerType()).isEqualTo(ManufacturerType.PRIVATE);
        assertThat(dto.getLaneOne()).isEqualTo("Lane1");
        assertThat(dto.getLaneTwo()).isEqualTo("Lane2");
        assertThat(dto.getVillageName()).isEqualTo("Village1");
        assertThat(dto.getPinCode()).isEqualTo("123456");
        assertThat(dto.getLongitude()).isEqualTo(12.34);
        assertThat(dto.getLatitude()).isEqualTo(56.78);
        assertThat(dto.getDistrictName()).isEqualTo("District1");
        assertThat(dto.getStateName()).isEqualTo("State1");
        assertThat(dto.getCountryName()).isEqualTo("Country1");
        assertThat(dto.getUserName()).isEqualTo("User1");
        assertThat(dto.getEmailAddress()).isEqualTo("user@example.com");
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
    }


    @Test
    void testToString() {
        List<String> categories = List.of("Category1", "Category2");
        FssaiManufacturerRequestDto dto = new FssaiManufacturerRequestDto(categories, "Manufacturer1", "Type1", true,
                VendorType.Manufacturer, "Agency1", "License123", ManufacturerType.PRIVATE, "Lane1", "Lane2",
                "Village1", "123456", 12.34, 56.78, "District1", "State1", "Country1", "User1", "user@example.com",
                "John", "Doe");

        String dtoString = dto.toString();

    }
}
