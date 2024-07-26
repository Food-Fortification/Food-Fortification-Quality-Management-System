package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalMetaDataRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        ExternalMetaDataRequestDto dto = new ExternalMetaDataRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getValue()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        ExternalMetaDataRequestDto dto = new ExternalMetaDataRequestDto(1L, "TestName", "TestValue");
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("TestName");
        assertThat(dto.getValue()).isEqualTo("TestValue");
    }

    @Test
    void testSettersAndGetters() {
        ExternalMetaDataRequestDto dto = new ExternalMetaDataRequestDto();
        dto.setId(1L);
        dto.setName("TestName");
        dto.setValue("TestValue");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("TestName");
        assertThat(dto.getValue()).isEqualTo("TestValue");
    }

    @Test
    void testToString() {
        ExternalMetaDataRequestDto dto = new ExternalMetaDataRequestDto(1L, "TestName", "TestValue");
        String expectedString = "ExternalMetaDataRequestDto(id=1, name=TestName, value=TestValue)";
        assertThat(dto.toString()).isEqualTo(expectedString);
    }

    @Test
    void testEqualsAndHashCode() {
        ExternalMetaDataRequestDto dto1 = new ExternalMetaDataRequestDto(1L, "TestName", "TestValue");
        ExternalMetaDataRequestDto dto2 = new ExternalMetaDataRequestDto(1L, "TestName", "TestValue");
        ExternalMetaDataRequestDto dto3 = new ExternalMetaDataRequestDto(2L, "AnotherName", "AnotherValue");

        assertThat(dto1).isNotEqualTo(dto3);
    }
}
