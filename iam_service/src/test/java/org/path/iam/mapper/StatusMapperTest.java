package org.path.iam.mapper;

import org.path.iam.dto.requestDto.StatusRequestDto;
import org.path.iam.dto.responseDto.StatusResponseDto;
import org.path.iam.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusMapperTest {

    @Test
    void testToDto() {
        // Create a Status entity
        Status status = new Status();
        status.setId(1L);
        status.setName("Active");
        status.setDescription("The entity is currently active.");

        // Create a StatusMapper instance
        StatusMapper statusMapper = new StatusMapper();

        // Perform mapping
        StatusResponseDto responseDto = statusMapper.toDto(status);

        // Assert
        assertEquals(1L, responseDto.getId());
        assertEquals("Active", responseDto.getName());
        assertEquals("The entity is currently active.", responseDto.getDescription());
    }

    @Test
    void testToEntity() {
        // Create a StatusRequestDto
        StatusRequestDto requestDto = new StatusRequestDto();
        requestDto.setId(1L);
        requestDto.setName("Active");
        requestDto.setDescription("The entity is currently active.");

        // Create a StatusMapper instance
        StatusMapper statusMapper = new StatusMapper();

        // Perform mapping
        Status entity = statusMapper.toEntity(requestDto);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("Active", entity.getName());
        assertEquals("The entity is currently active.", entity.getDescription());
    }
}
