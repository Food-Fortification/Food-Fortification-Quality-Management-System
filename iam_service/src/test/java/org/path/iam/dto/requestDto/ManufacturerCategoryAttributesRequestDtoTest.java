package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerCategoryAttributesRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        Long manufacturerId = 2L;
        Long userId = 3L;

        Set<AttributeCategoryScoreRequestDto> attributeCategoryScores = new HashSet<>();
        // Add at least one AttributeCategoryScoreRequestDto object here (mock or simple instance)

        ManufacturerCategoryAttributesRequestDto requestDto = new ManufacturerCategoryAttributesRequestDto(
                id, manufacturerId, userId, attributeCategoryScores);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(manufacturerId, requestDto.getManufacturerId());
        assertEquals(userId, requestDto.getUserId());
        assertNotNull(requestDto.getAttributeCategoryScores());
        assertTrue(requestDto.getAttributeCategoryScores().isEmpty());
    }

    @Test
    public void testSetters() {
        ManufacturerCategoryAttributesRequestDto requestDto = new ManufacturerCategoryAttributesRequestDto();

        Long newManufacturerId = 4L;
        Set<AttributeCategoryScoreRequestDto> newAttributeCategoryScores = new HashSet<>();
        // Add different AttributeCategoryScoreRequestDto objects here

        requestDto.setManufacturerId(newManufacturerId);
        requestDto.setAttributeCategoryScores(newAttributeCategoryScores);

        assertEquals(newManufacturerId, requestDto.getManufacturerId());
    }
}
