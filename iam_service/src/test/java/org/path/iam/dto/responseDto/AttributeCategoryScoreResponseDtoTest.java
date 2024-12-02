package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AttributeCategoryScoreResponseDtoTest {

    @Test
    void testValidConstructionAndGetters() {
        Long id = 1L;
        AttributeCategoryResponseDto attributeCategory = new AttributeCategoryResponseDto();
        Set<ManufacturerAttributeScoreResponseDto> attributeScore = new HashSet<>();
        attributeScore.add(new ManufacturerAttributeScoreResponseDto(3L, null, null, null, null));
        attributeScore.add(new ManufacturerAttributeScoreResponseDto(4L, null, null, null, null));
        ManufacturerCategoryAttributesResponseDto manufacturerCategoryAttributes = new ManufacturerCategoryAttributesResponseDto();

        AttributeCategoryScoreResponseDto responseDto = new AttributeCategoryScoreResponseDto(id, attributeCategory, attributeScore, manufacturerCategoryAttributes);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(attributeCategory, responseDto.getAttributeCategory());
        assertEquals(attributeScore, responseDto.getAttributeScore());
        assertEquals(manufacturerCategoryAttributes, responseDto.getManufacturerCategoryAttributes());
    }

    @Test
    void testWithEmptySets() {
        Long id = 2L;
        AttributeCategoryResponseDto attributeCategory = new AttributeCategoryResponseDto(null, "Address", null);
        Set<ManufacturerAttributeScoreResponseDto> attributeScore = new HashSet<>();
        ManufacturerCategoryAttributesResponseDto manufacturerCategoryAttributes = null;

        AttributeCategoryScoreResponseDto responseDto = new AttributeCategoryScoreResponseDto(id, attributeCategory, attributeScore, manufacturerCategoryAttributes);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(attributeCategory, responseDto.getAttributeCategory());
        assertTrue(responseDto.getAttributeScore().isEmpty());
        assertNull(responseDto.getManufacturerCategoryAttributes());
    }

    @Test
    void testJsonIgnoreProperties() throws Exception {
        Long id = 1L;
        AttributeCategoryResponseDto attributeCategory = new AttributeCategoryResponseDto();
        Set<ManufacturerAttributeScoreResponseDto> attributeScore = new HashSet<>();
        attributeScore.add(new ManufacturerAttributeScoreResponseDto(3L, null, null, null, null));
        attributeScore.add(new ManufacturerAttributeScoreResponseDto(4L, null, null, null, null));
        ManufacturerCategoryAttributesResponseDto manufacturerCategoryAttributes = new ManufacturerCategoryAttributesResponseDto(5L, null, null, null);

        AttributeCategoryScoreResponseDto responseDto = new AttributeCategoryScoreResponseDto(id, attributeCategory, attributeScore, manufacturerCategoryAttributes);

        // Simulate serialization to JSON (replace with your actual library)
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseDto);

        // Assert that excluded properties are not present in JSON due to @JsonIgnoreProperties
        assertFalse(json.contains("\"attributes\""));
        assertFalse(json.contains("\"attributeCategoryScores\""));
    }
}
