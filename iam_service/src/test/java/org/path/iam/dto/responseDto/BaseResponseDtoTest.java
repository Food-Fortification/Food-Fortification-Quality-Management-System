package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        StatusResponseDto status = new StatusResponseDto();

        BaseResponseDto responseDto = new BaseResponseDto(uuid, status);

        assertNotNull(responseDto);
        assertEquals(uuid, responseDto.getUuid());
        assertEquals(status, responseDto.getStatus());
    }

    @Test
    public void testWithOptionalFields() {
        String uuid = null;
        StatusResponseDto status = null;

        BaseResponseDto responseDto = new BaseResponseDto(uuid, status);

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid());
        assertNull(responseDto.getStatus());
    }

    @Test
    public void testJsonIncludeProperties() throws Exception {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        StatusResponseDto status = new StatusResponseDto();

        BaseResponseDto responseDto = new BaseResponseDto(uuid, status);

        // Simulate serialization to JSON (replace with your actual library)
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseDto);

        // Assert that only specified properties from @JsonIncludeProperties are present in JSON for status object
        assertTrue(json.contains("\"id\":"));
        assertTrue(json.contains("\"name\":"));
        assertFalse(json.contains("\"description\":")); // Assuming no "description" property in StatusResponseDto
    }
}
