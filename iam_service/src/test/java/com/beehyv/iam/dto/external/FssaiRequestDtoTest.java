package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FssaiRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        FssaiRequestDto dto = new FssaiRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getRequestID()).isNull();
        assertThat(dto.getUserDetails()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        UserDetailsRequestDto userDetails = new UserDetailsRequestDto(); // Assume this is properly instantiated
        FssaiRequestDto dto = new FssaiRequestDto("12345", userDetails);

        assertThat(dto.getRequestID()).isEqualTo("12345");
        assertThat(dto.getUserDetails()).isEqualTo(userDetails);
    }

    @Test
    void testSettersAndGetters() {
        FssaiRequestDto dto = new FssaiRequestDto();
        UserDetailsRequestDto userDetails = new UserDetailsRequestDto(); // Assume this is properly instantiated

        dto.setRequestID("67890");
        dto.setUserDetails(userDetails);

        assertThat(dto.getRequestID()).isEqualTo("67890");
        assertThat(dto.getUserDetails()).isEqualTo(userDetails);
    }

    @Test
    void testToString() {
        UserDetailsRequestDto userDetails = new UserDetailsRequestDto(); // Assume this is properly instantiated
        FssaiRequestDto dto = new FssaiRequestDto("54321", userDetails);

        String dtoString = dto.toString();
        assertThat(dtoString).contains("54321");
        assertThat(dtoString).contains(userDetails.toString());
    }
}
