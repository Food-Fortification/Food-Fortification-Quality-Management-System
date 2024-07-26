package com.beehyv.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        // Given
        StatusResponseDto statusResponseDto = new StatusResponseDto();
        Long id = 1L;
        String name = "TestStatus";
        String description = "Test description";

        // When
        statusResponseDto.setId(id);
        statusResponseDto.setName(name);
        statusResponseDto.setDescription(description);

        // Then
        assertEquals(id, statusResponseDto.getId());
        assertEquals(name, statusResponseDto.getName());
        assertEquals(description, statusResponseDto.getDescription());
    }
}
