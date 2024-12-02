package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AttributeCategoryRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        AttributeCategoryRequestDto dto = new AttributeCategoryRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getCategory()).isNull();
        assertThat(dto.getAttributes()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        AttributeRequestDto attribute1 = new AttributeRequestDto(1L, "Attribute1", null, null, null, null, null, null);
        AttributeRequestDto attribute2 = new AttributeRequestDto(2L, "Attribute2", null, null, null, null, null, null);
        Set<AttributeRequestDto> attributes = new HashSet<>();
        attributes.add(attribute1);
        attributes.add(attribute2);

        AttributeCategoryRequestDto dto = new AttributeCategoryRequestDto(100L, "Category1", attributes);

        assertThat(dto.getId()).isEqualTo(100L);
        assertThat(dto.getCategory()).isEqualTo("Category1");
        assertThat(dto.getAttributes()).containsExactlyInAnyOrder(attribute1, attribute2);
    }

    @Test
    void testSettersAndGetters() {
        AttributeCategoryRequestDto dto = new AttributeCategoryRequestDto();
        AttributeRequestDto attribute1 = new AttributeRequestDto(1L, "Attribute1", null, null, null, null, null, null);
        AttributeRequestDto attribute2 = new AttributeRequestDto(2L, "Attribute2", null, null, null, null, null, null);
        Set<AttributeRequestDto> attributes = new HashSet<>();
        attributes.add(attribute1);
        attributes.add(attribute2);

        dto.setId(101L);
        dto.setCategory("Category2");
        dto.setAttributes(attributes);

        assertThat(dto.getId()).isEqualTo(101L);
        assertThat(dto.getCategory()).isEqualTo("Category2");
        assertThat(dto.getAttributes()).containsExactlyInAnyOrder(attribute1, attribute2);
    }

    @Test
    void testToString() {
        AttributeRequestDto attribute1 = new AttributeRequestDto(1L, "Attribute1", null, null, null, null, null, null);
        AttributeRequestDto attribute2 = new AttributeRequestDto(2L, "Attribute2", null, null, null, null, null, null);
        Set<AttributeRequestDto> attributes = new HashSet<>();
        attributes.add(attribute1);
        attributes.add(attribute2);

        AttributeCategoryRequestDto dto = new AttributeCategoryRequestDto(102L, "Category3", attributes);

        String dtoString = dto.toString();
        assertThat(dtoString).contains("Attribute1");
        assertThat(dtoString).contains("Attribute2");
        assertThat(dtoString).contains("Category3");

    }
}
