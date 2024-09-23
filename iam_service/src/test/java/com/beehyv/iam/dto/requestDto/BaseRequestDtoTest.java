package com.beehyv.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaseRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        BaseRequestDto dto = new BaseRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getStatusId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        BaseRequestDto dto = new BaseRequestDto(1L);
        assertThat(dto.getStatusId()).isEqualTo(1L);
    }

    @Test
    void testSettersAndGetters() {
        BaseRequestDto dto = new BaseRequestDto();
        dto.setStatusId(2L);
        assertThat(dto.getStatusId()).isEqualTo(2L);
    }


}
