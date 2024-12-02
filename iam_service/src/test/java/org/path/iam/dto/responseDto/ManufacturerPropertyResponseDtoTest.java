package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerPropertyResponseDtoTest {

    private ManufacturerPropertyResponseDto manufacturerPropertyResponseDto;

    @BeforeEach
    public void setUp() {
        manufacturerPropertyResponseDto = new ManufacturerPropertyResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerPropertyResponseDto dto = new ManufacturerPropertyResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getValue());
    }

    @Test
    public void testAllArgsConstructor() {
        ManufacturerPropertyResponseDto dto = new ManufacturerPropertyResponseDto(1L, "Property Name", "Property Value");

        assertEquals(1L, dto.getId());
        assertEquals("Property Name", dto.getName());
        assertEquals("Property Value", dto.getValue());
    }

    @Test
    public void testSettersAndGetters() {
        manufacturerPropertyResponseDto.setId(1L);
        manufacturerPropertyResponseDto.setName("Property Name");
        manufacturerPropertyResponseDto.setValue("Property Value");

        assertEquals(1L, manufacturerPropertyResponseDto.getId());
        assertEquals("Property Name", manufacturerPropertyResponseDto.getName());
        assertEquals("Property Value", manufacturerPropertyResponseDto.getValue());
    }
}
