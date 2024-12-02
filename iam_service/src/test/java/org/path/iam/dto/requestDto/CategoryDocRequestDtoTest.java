package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryDocRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        CategoryDocRequestDto dto = new CategoryDocRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getCategoryId()).isNull();
        assertThat(dto.getDocTypeId()).isNull();
        assertThat(dto.getIsMandatory()).isNull();
        assertThat(dto.getIsEnabled()).isNull();
        assertThat(dto.getStatusId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        CategoryDocRequestDto dto = new CategoryDocRequestDto(1L, 2L, 3L, true, false);

    }

    @Test
    void testSettersAndGetters() {
        CategoryDocRequestDto dto = new CategoryDocRequestDto();

        dto.setId(5L);
        dto.setCategoryId(6L);
        dto.setDocTypeId(7L);
        dto.setIsMandatory(true);
        dto.setIsEnabled(false);
        dto.setStatusId(8L);

        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getCategoryId()).isEqualTo(6L);
        assertThat(dto.getDocTypeId()).isEqualTo(7L);
        assertThat(dto.getIsMandatory()).isTrue();
        assertThat(dto.getIsEnabled()).isFalse();
        assertThat(dto.getStatusId()).isEqualTo(8L);
    }

    @Test
    void testToString() {
        CategoryDocRequestDto dto = new CategoryDocRequestDto(1L, 2L, 3L, true, false);
        String dtoString = dto.toString();

    }
}
