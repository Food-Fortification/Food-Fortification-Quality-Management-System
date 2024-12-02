package org.path.lab.dto.requestDto;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WastageRequestDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void validateWastageRequestDto_WithValidInput_ReturnsNoValidationErrors() {
        // Given
        WastageRequestDto dto = new WastageRequestDto();
        dto.setId(1L);
        dto.setBatchId(1L);
        dto.setLotId(1L);
        dto.setWastageQuantity(10.5);
        dto.setReportedDate(new Date());
        dto.setComments("Test comments");
        dto.setUomId(1L);

        // When
        var violations = validator.validate(dto);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validateWastageRequestDto_WithNegativeQuantity_ReturnsValidationError() {
        // Given
        WastageRequestDto dto = new WastageRequestDto();
        dto.setWastageQuantity(-10.5);

        // When
        var violations = validator.validate(dto);

        // Then
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateWastageRequestDto_WithNullUomId_ReturnsValidationError() {
        // Given
        WastageRequestDto dto = new WastageRequestDto();
        dto.setUomId(null);

        // When
        var violations = validator.validate(dto);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals("Uom id cannot be null", violations.iterator().next().getMessage());
    }
}
