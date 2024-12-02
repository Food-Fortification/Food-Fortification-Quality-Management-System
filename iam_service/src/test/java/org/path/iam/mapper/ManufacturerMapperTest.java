package org.path.iam.mapper;

import org.path.iam.dto.requestDto.ManufacturerRequestDto;
import org.path.iam.dto.responseDto.ManufacturerResponseDto;
import org.path.iam.model.Manufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerMapperTest {

    private ManufacturerMapper manufacturerMapper;
    @Mock
    private ManufacturerRequestDto manufacturerRequestDto;

    @BeforeEach
    void setUp() {
        manufacturerMapper = new ManufacturerMapper();
    }

    @Test
    void testToDto() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        manufacturer.setName("Test Manufacturer");
        // Set other fields of Manufacturer object

        // Perform mapping
        ManufacturerResponseDto manufacturerResponseDto = manufacturerMapper.toDto(manufacturer);

        // Assertions
        assertEquals(1L, manufacturerResponseDto.getId());
        assertEquals("Test Manufacturer", manufacturerResponseDto.getName());
        // Add more assertions for other fields
    }

    @Test
    void testToEntity() {
        // Mocking DTO
        when(manufacturerRequestDto.getId()).thenReturn(1L);
        when(manufacturerRequestDto.getName()).thenReturn("Test Manufacturer");
        // Mock other relevant DTO methods

        // Create ManufacturerMapper instance
        ManufacturerMapper manufacturerMapper = new ManufacturerMapper();

        // Perform mapping
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerRequestDto);

        // Assertions
        assertEquals(1L, manufacturer.getId());
        assertEquals("Test Manufacturer", manufacturer.getName());
        // Add more assertions for other fields
    }

    // Add more test methods for other mapping scenarios (e.g., testToDto)
}

