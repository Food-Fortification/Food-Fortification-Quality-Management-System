package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistrictRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        DistrictRequestDto dto = new DistrictRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getStateId()).isNull();
        assertThat(dto.getStatusId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        DistrictRequestDto dto = new DistrictRequestDto(1L, "District 1", 2L);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("District 1");
        assertThat(dto.getStateId()).isEqualTo(2L);
    }

    @Test
    void testSettersAndGetters() {
        DistrictRequestDto dto = new DistrictRequestDto();

        dto.setId(4L);
        dto.setName("District 2");
        dto.setStateId(5L);
        dto.setStatusId(6L);

        assertThat(dto.getId()).isEqualTo(4L);
        assertThat(dto.getName()).isEqualTo("District 2");
        assertThat(dto.getStateId()).isEqualTo(5L);
        assertThat(dto.getStatusId()).isEqualTo(6L);
    }

    @Test
    void testToString() {
        DistrictRequestDto dto = new DistrictRequestDto(1L, "District 1", 2L);
        String dtoString = dto.toString();
    }
}
