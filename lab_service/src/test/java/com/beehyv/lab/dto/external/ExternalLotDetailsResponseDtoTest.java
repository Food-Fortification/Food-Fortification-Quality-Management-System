package com.beehyv.lab.dto.external;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalLotDetailsResponseDtoTest {

    @Test
    void constructorAndGetters_Success() {
        // Given
        Long manufacturerId = 123L;
        Long categoryId = 456L;
        Long lotId = 789L;

        // When
        ExternalLotDetailsResponseDto dto = new ExternalLotDetailsResponseDto(manufacturerId, categoryId, lotId);

        // Then
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(lotId, dto.getLotId());
    }

    @Test
    void settersAndGetters_Success() {
        // Given
        Long manufacturerId = 123L;
        Long categoryId = 456L;
        Long lotId = 789L;
        ExternalLotDetailsResponseDto dto = new ExternalLotDetailsResponseDto();

        // When
        dto.setManufacturerId(manufacturerId);
        dto.setCategoryId(categoryId);
        dto.setLotId(lotId);

        // Then
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(lotId, dto.getLotId());
    }

    @Test
    void toString_ReturnsExpectedString() {
        // Given
        Long manufacturerId = 123L;
        Long categoryId = 456L;
        Long lotId = 789L;
        ExternalLotDetailsResponseDto dto = new ExternalLotDetailsResponseDto(manufacturerId, categoryId, lotId);

        // When
        String expectedString = "ExternalLotDetailsResponseDto(manufacturerId=123, categoryId=456, lotId=789)";
        String actualString = dto.toString();

        // Then
        assertEquals(expectedString, actualString);
    }

    @Test
    void noArgsConstructor_DoesNotThrowException() {
        // When
        ExternalLotDetailsResponseDto dto = new ExternalLotDetailsResponseDto();

        // Then
        // No exception should be thrown
    }

    @Test
    void allArgsConstructor_DoesNotThrowException() {
        // Given
        Long manufacturerId = 123L;
        Long categoryId = 456L;
        Long lotId = 789L;

        // When
        ExternalLotDetailsResponseDto dto = new ExternalLotDetailsResponseDto(manufacturerId, categoryId, lotId);

        // Then
        // No exception should be thrown
    }
}
