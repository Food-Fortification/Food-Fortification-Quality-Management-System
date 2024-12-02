package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AttributeCategoryResponseDtoTest {

    @Test
    void testValidConstructionAndGetters() {
        Long id = 1L;
        String category = "Personal Details";
        Set<AttributeResponseDto> attributes = new HashSet<>();
        attributes.add(new AttributeResponseDto(2L, "Name", true, null, null, null, null, null));
        attributes.add(new AttributeResponseDto(3L, "Email", true, null, null, null, null, null));

        AttributeCategoryResponseDto responseDto = new AttributeCategoryResponseDto(id, category, attributes);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(category, responseDto.getCategory());
        assertEquals(attributes, responseDto.getAttributes());
    }

    @Test
    void testWithEmptyAttributes() {
        Long id = 2L;
        String category = "Address";
        Set<AttributeResponseDto> attributes = new HashSet<>();

        AttributeCategoryResponseDto responseDto = new AttributeCategoryResponseDto(id, category, attributes);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(category, responseDto.getCategory());
        assertTrue(responseDto.getAttributes().isEmpty());
    }

    @Test
    void testJsonIgnoreProperties() throws Exception {
        Long id = 1L;
        String category = "Personal Details";
        Set<AttributeResponseDto> attributes = new HashSet<>();
        attributes.add(new AttributeResponseDto(2L, "Name", true, null, null, null, null, null));
        attributes.add(new AttributeResponseDto(3L, "Email", true, null, null, null, null, null));

        AttributeCategoryResponseDto responseDto = new AttributeCategoryResponseDto(id, category, attributes);

        // Simulate serialization to JSON (replace with your actual library)
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseDto);

        // Assert that "attributeCategory" is not present in the serialized JSON due to @JsonIgnoreProperties
        assertFalse(json.contains("\"attributeCategory\""));
    }
}
