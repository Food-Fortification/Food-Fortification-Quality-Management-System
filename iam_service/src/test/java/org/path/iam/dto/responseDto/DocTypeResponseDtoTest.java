package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocTypeResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        Long id = 1L;
        String name = "Aadhar Card";

        DocTypeResponseDto responseDto = new DocTypeResponseDto(id, name);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(name, responseDto.getName());
    }

    @Test
    public void testWithOptionalFields() {
        String uuid = null;
        DocTypeResponseDto responseDto = new DocTypeResponseDto(2L, "Passport");

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid());
        assertEquals(2L, responseDto.getId());
        assertEquals("Passport", responseDto.getName());
    }

    @Test
    public void testBaseResponseDtoFields() {
        // Inherits from BaseResponseDto, test its fields here
        StatusResponseDto status = new StatusResponseDto();

        DocTypeResponseDto responseDto = new DocTypeResponseDto(3L, "PAN Card");

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid()); // Can be null if not set
    }
}
