package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerPropertyRequestDtoTest {

    private final Validator validator;

    public ManufacturerPropertyRequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerPropertyRequestDto dto = new ManufacturerPropertyRequestDto();
        assertNull(dto.getId());
        assertNull(dto.getManufacturerId());
        assertNull(dto.getName());
        assertNull(dto.getValue());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        Long manufacturerId = 2L;
        String name = "Property Name";
        String value = "Property Value";

        ManufacturerPropertyRequestDto dto = new ManufacturerPropertyRequestDto(id, manufacturerId, name, value);

        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(name, dto.getName());
        assertEquals(value, dto.getValue());
    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerPropertyRequestDto dto = new ManufacturerPropertyRequestDto();
        Long id = 1L;
        Long manufacturerId = 2L;
        String name = "Property Name";
        String value = "Property Value";

        dto.setId(id);
        dto.setManufacturerId(manufacturerId);
        dto.setName(name);
        dto.setValue(value);

        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(name, dto.getName());
        assertEquals(value, dto.getValue());
    }

    @Test
    public void testValidation_ManufacturerIdNotNull() {
        ManufacturerPropertyRequestDto dto = new ManufacturerPropertyRequestDto();
        dto.setId(1L);
        dto.setName("Property Name");
        dto.setValue("Property Value");

        Set<ConstraintViolation<ManufacturerPropertyRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        ConstraintViolation<ManufacturerPropertyRequestDto> violation = violations.iterator().next();
        assertEquals("Manufacturer id cannot be null", violation.getMessage());
    }

    @Test
    public void testValidation_ManufacturerIdPresent() {
        ManufacturerPropertyRequestDto dto = new ManufacturerPropertyRequestDto();
        dto.setId(1L);
        dto.setManufacturerId(2L);
        dto.setName("Property Name");
        dto.setValue("Property Value");

        Set<ConstraintViolation<ManufacturerPropertyRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEqualsAndHashCode() {
        Long id = 1L;
        Long manufacturerId = 2L;
        String name = "Property Name";
        String value = "Property Value";

        ManufacturerPropertyRequestDto dto1 = new ManufacturerPropertyRequestDto(id, manufacturerId, name, value);
        ManufacturerPropertyRequestDto dto2 = new ManufacturerPropertyRequestDto(id, manufacturerId, name, value);
        ManufacturerPropertyRequestDto dto3 = new ManufacturerPropertyRequestDto();

        assertNotEquals(dto1, dto3);
    }
}
