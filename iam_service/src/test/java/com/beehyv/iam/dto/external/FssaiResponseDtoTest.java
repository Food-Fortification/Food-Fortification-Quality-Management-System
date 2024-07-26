package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FssaiResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        FssaiResponseDto dto = new FssaiResponseDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getStatusCode()).isNull();
        assertThat(dto.getRedirectionUrl()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        FssaiResponseDto dto = new FssaiResponseDto("200", "http://example.com");

        assertThat(dto.getStatusCode()).isEqualTo("200");
        assertThat(dto.getRedirectionUrl()).isEqualTo("http://example.com");
    }

    @Test
    void testSettersAndGetters() {
        FssaiResponseDto dto = new FssaiResponseDto();

        dto.setStatusCode("404");
        dto.setRedirectionUrl("http://error.com");

        assertThat(dto.getStatusCode()).isEqualTo("404");
        assertThat(dto.getRedirectionUrl()).isEqualTo("http://error.com");
    }

    @Test
    void testToString() {
        FssaiResponseDto dto = new FssaiResponseDto("500", "http://server-error.com");

        String dtoString = dto.toString();
        assertThat(dtoString).contains("500");
        assertThat(dtoString).contains("http://server-error.com");
    }
}
