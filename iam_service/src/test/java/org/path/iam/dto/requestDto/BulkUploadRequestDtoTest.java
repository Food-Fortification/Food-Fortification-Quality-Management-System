package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BulkUploadRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        BulkUploadRequestDto dto = new BulkUploadRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getCompleteAddress()).isNull();
        assertThat(dto.getLicenseNumber()).isNull();
        assertThat(dto.getType()).isNull();
        assertThat(dto.getStatus()).isNull();
        assertThat(dto.getStatusId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        BulkUploadRequestDto dto = new BulkUploadRequestDto(
                "Test Name", "Test Address", "License123", "Type1", "Status1"
        );


    }

    @Test
    void testSettersAndGetters() {
        BulkUploadRequestDto dto = new BulkUploadRequestDto();

        dto.setName("Updated Name");
        dto.setCompleteAddress("Updated Address");
        dto.setLicenseNumber("Updated License");
        dto.setType("Updated Type");
        dto.setStatus("Updated Status");
        dto.setStatusId(2L);

        assertThat(dto.getName()).isEqualTo("Updated Name");
        assertThat(dto.getCompleteAddress()).isEqualTo("Updated Address");
        assertThat(dto.getLicenseNumber()).isEqualTo("Updated License");
        assertThat(dto.getType()).isEqualTo("Updated Type");
        assertThat(dto.getStatus()).isEqualTo("Updated Status");
        assertThat(dto.getStatusId()).isEqualTo(2L);
    }

    @Test
    void testToString() {
        BulkUploadRequestDto dto = new BulkUploadRequestDto(
                "Test Name", "Test Address", "License123", "Type1", "Status1"
        );

        String dtoString = dto.toString();

    }
}
