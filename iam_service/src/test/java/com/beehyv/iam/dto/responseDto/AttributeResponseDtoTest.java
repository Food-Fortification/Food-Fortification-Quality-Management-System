package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.AttributeScoreType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        String name = "Name";
        boolean isActive = true;
        Double weightage = 0.5;
        int totalScore = 10;
        int defaultScore = 5;
        AttributeScoreType type = AttributeScoreType.BOOLEAN;
        AttributeCategoryResponseDto attributeCategory = new AttributeCategoryResponseDto();

        AttributeResponseDto responseDto = new AttributeResponseDto(id, name, isActive, weightage, totalScore, defaultScore, type, attributeCategory);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(name, responseDto.getName());
        assertEquals(isActive, responseDto.getIsActive());
        assertEquals(weightage, responseDto.getWeightage());
        assertEquals(totalScore, responseDto.getTotalScore());
        assertEquals(defaultScore, responseDto.getDefaultScore());
        assertEquals(type, responseDto.getType());
        assertEquals(attributeCategory, responseDto.getAttributeCategory());
    }

    @Test
    public void testWithOptionalFields() {
        Long id = 2L;
        String name = "Email";
        boolean isActive = false;
        Double weightage = null;
        int totalScore = 0;
        Integer defaultScore = null;
        AttributeScoreType type = AttributeScoreType.BOOLEAN;
        AttributeCategoryResponseDto attributeCategory = null;

        AttributeResponseDto responseDto = new AttributeResponseDto(id, name, isActive, weightage, totalScore, defaultScore, type, attributeCategory);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(name, responseDto.getName());
        assertEquals(isActive, responseDto.getIsActive());
        assertNull(responseDto.getWeightage());
        assertEquals(totalScore, responseDto.getTotalScore());
        assertNull(responseDto.getDefaultScore());
        assertEquals(type, responseDto.getType());
        assertNull(responseDto.getAttributeCategory());
    }

    @Test
    public void testJsonIgnoreProperties() throws Exception {
        Long id = 1L;
        String name = "Name";
        boolean isActive = true;
        Double weightage = 0.5;
        int totalScore = 10;
        int defaultScore = 5;
        AttributeScoreType type = AttributeScoreType.BOOLEAN;
        AttributeCategoryResponseDto attributeCategory = new AttributeCategoryResponseDto();

        AttributeResponseDto responseDto = new AttributeResponseDto(id, name, isActive, weightage, totalScore, defaultScore, type, attributeCategory);

        // Simulate serialization to JSON (replace with your actual library)
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseDto);

        // Assert that "attributes" is not present in the serialized JSON due to @JsonIgnoreProperties
        assertFalse(json.contains("\"attributes\""));
    }
}
