package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AttributeCategoryScoreRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        AttributeCategoryScoreRequestDto dto = new AttributeCategoryScoreRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getAttributeCategoryId()).isNull();
        assertThat(dto.getManufacturerAttributeScores()).isNull();
        assertThat(dto.getManufacturerCategoryAttributesId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        ManufacturerAttributeScoreRequestDto score1 = new ManufacturerAttributeScoreRequestDto(1L, 80L, null, null, null);
        ManufacturerAttributeScoreRequestDto score2 = new ManufacturerAttributeScoreRequestDto(2L, 80L, null, null, null);
        Set<ManufacturerAttributeScoreRequestDto> scores = new HashSet<>();
        scores.add(score1);
        scores.add(score2);

        AttributeCategoryScoreRequestDto dto = new AttributeCategoryScoreRequestDto(100L, 200L, scores, 300L);

        assertThat(dto.getId()).isEqualTo(100L);
        assertThat(dto.getAttributeCategoryId()).isEqualTo(200L);
        assertThat(dto.getManufacturerAttributeScores()).containsExactlyInAnyOrder(score1, score2);
        assertThat(dto.getManufacturerCategoryAttributesId()).isEqualTo(300L);
    }

    @Test
    void testSettersAndGetters() {
        AttributeCategoryScoreRequestDto dto = new AttributeCategoryScoreRequestDto();
        ManufacturerAttributeScoreRequestDto score1 = new ManufacturerAttributeScoreRequestDto(1L, 80L, null, null, null);
        ManufacturerAttributeScoreRequestDto score2 = new ManufacturerAttributeScoreRequestDto(2L, 80L, null, null, null);
        Set<ManufacturerAttributeScoreRequestDto> scores = new HashSet<>();
        scores.add(score1);
        scores.add(score2);

        dto.setId(101L);
        dto.setAttributeCategoryId(201L);
        dto.setManufacturerAttributeScores(scores);
        dto.setManufacturerCategoryAttributesId(301L);

        assertThat(dto.getId()).isEqualTo(101L);
        assertThat(dto.getAttributeCategoryId()).isEqualTo(201L);
        assertThat(dto.getManufacturerAttributeScores()).containsExactlyInAnyOrder(score1, score2);
        assertThat(dto.getManufacturerCategoryAttributesId()).isEqualTo(301L);
    }

    @Test
    void testToString() {
        ManufacturerAttributeScoreRequestDto score1 = new ManufacturerAttributeScoreRequestDto(1L, 80L, null, null, null);
        ManufacturerAttributeScoreRequestDto score2 = new ManufacturerAttributeScoreRequestDto(2L, 80L, null, null, null);
        Set<ManufacturerAttributeScoreRequestDto> scores = new HashSet<>();
        scores.add(score1);
        scores.add(score2);

        AttributeCategoryScoreRequestDto dto = new AttributeCategoryScoreRequestDto(102L, 202L, scores, 302L);

        String dtoString = dto.toString();

        assertThat(dtoString).contains("102");
    }
}
