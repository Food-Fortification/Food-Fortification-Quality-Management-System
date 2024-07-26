package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserDetailsRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        UserDetailsRequestDto dto = new UserDetailsRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getApplication_no()).isNull();
        assertThat(dto.getLicense_no()).isNull();
        assertThat(dto.getFbo_id()).isNull();
        assertThat(dto.getFbo_name()).isNull();
        assertThat(dto.getExpiry_date()).isNull();
        assertThat(dto.getStatus()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Date expiryDate = new Date();
        UserDetailsRequestDto dto = new UserDetailsRequestDto(
                "APP123", "LIC456", "FBO789", "FBO Name", expiryDate, "Active"
        );

        assertThat(dto.getApplication_no()).isEqualTo("APP123");
        assertThat(dto.getLicense_no()).isEqualTo("LIC456");
        assertThat(dto.getFbo_id()).isEqualTo("FBO789");
        assertThat(dto.getFbo_name()).isEqualTo("FBO Name");
        assertThat(dto.getExpiry_date()).isEqualTo(expiryDate);
        assertThat(dto.getStatus()).isEqualTo("Active");
    }

    @Test
    void testSettersAndGetters() {
        UserDetailsRequestDto dto = new UserDetailsRequestDto();
        Date expiryDate = new Date();

        dto.setApplication_no("APP456");
        dto.setLicense_no("LIC789");
        dto.setFbo_id("FBO123");
        dto.setFbo_name("FBO Test Name");
        dto.setExpiry_date(expiryDate);
        dto.setStatus("Expired");

        assertThat(dto.getApplication_no()).isEqualTo("APP456");
        assertThat(dto.getLicense_no()).isEqualTo("LIC789");
        assertThat(dto.getFbo_id()).isEqualTo("FBO123");
        assertThat(dto.getFbo_name()).isEqualTo("FBO Test Name");
        assertThat(dto.getExpiry_date()).isEqualTo(expiryDate);
        assertThat(dto.getStatus()).isEqualTo("Expired");
    }

    @Test
    void testToString() {
        Date expiryDate = new Date();
        UserDetailsRequestDto dto = new UserDetailsRequestDto(
                "APP789", "LIC123", "FBO456", "FBO Another Name", expiryDate, "Inactive"
        );

        String dtoString = dto.toString();
        assertThat(dtoString).contains("APP789");
        assertThat(dtoString).contains("LIC123");
        assertThat(dtoString).contains("FBO456");
        assertThat(dtoString).contains("FBO Another Name");
        assertThat(dtoString).contains(expiryDate.toString());
        assertThat(dtoString).contains("Inactive");
    }
}
