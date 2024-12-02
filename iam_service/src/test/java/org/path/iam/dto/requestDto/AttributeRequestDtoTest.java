package org.path.iam.dto.requestDto;

import org.path.iam.enums.AttributeScoreType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttributeRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        AttributeRequestDto dto = new AttributeRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getIsActive()).isNull();
        assertThat(dto.getWeightage()).isNull();
        assertThat(dto.getTotalScore()).isNull();
        assertThat(dto.getDefaultScore()).isNull();
        assertThat(dto.getType()).isNull();
        assertThat(dto.getAttributeCategoryId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        AttributeRequestDto dto = new AttributeRequestDto(
                1L, "Test Attribute", true, 0.75, 100, 50, AttributeScoreType.SCORE, 10L
        );

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test Attribute");
        assertThat(dto.getIsActive()).isTrue();
        assertThat(dto.getWeightage()).isEqualTo(0.75);
        assertThat(dto.getTotalScore()).isEqualTo(100);
        assertThat(dto.getDefaultScore()).isEqualTo(50);
        assertThat(dto.getType()).isEqualTo(AttributeScoreType.SCORE);
        assertThat(dto.getAttributeCategoryId()).isEqualTo(10L);
    }

    @Test
    void testSettersAndGetters() {
        AttributeRequestDto dto = new AttributeRequestDto();

        dto.setId(2L);
        dto.setName("Updated Attribute");
        dto.setIsActive(false);
        dto.setWeightage(0.85);
        dto.setTotalScore(200);
        dto.setDefaultScore(100);
        dto.setType(AttributeScoreType.SCORE);
        dto.setAttributeCategoryId(20L);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Updated Attribute");
        assertThat(dto.getIsActive()).isFalse();
        assertThat(dto.getWeightage()).isEqualTo(0.85);
        assertThat(dto.getTotalScore()).isEqualTo(200);
        assertThat(dto.getDefaultScore()).isEqualTo(100);
        assertThat(dto.getType()).isEqualTo(AttributeScoreType.SCORE);
        assertThat(dto.getAttributeCategoryId()).isEqualTo(20L);
    }

    @Test
    void testToString() {
        AttributeRequestDto dto = new AttributeRequestDto(
                3L, "String Attribute", true, 0.90, 150, 75, AttributeScoreType.SCORE, 30L
        );

        String dtoString = dto.toString();

    }
}
