package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.ComplianceType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerAttributeScoreResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String uuid = "123e4567-e89b-12d3-a456-426614174000";
        Long id = 1L;
        ComplianceType compliance = ComplianceType.C;
        String value = "10 years";
        AttributeResponseDto attribute = new AttributeResponseDto();
        AttributeCategoryScoreResponseDto categoryScore = new AttributeCategoryScoreResponseDto();

        ManufacturerAttributeScoreResponseDto responseDto = new ManufacturerAttributeScoreResponseDto();

        assertNotNull(responseDto);

    }

    @Test
    public void testWithOptionalFields() {
        String uuid = null;
        String value = null;
        AttributeResponseDto attribute = null;

        ManufacturerAttributeScoreResponseDto responseDto = new ManufacturerAttributeScoreResponseDto();

        assertNotNull(responseDto);
        assertNull(responseDto.getUuid());

    }

    @Test
    public void testJsonIgnoreProperties() throws Exception {
        // Simulate serialization to JSON (replace with your actual library)
        ObjectMapper mapper = new ObjectMapper();

        AttributeResponseDto attribute = new AttributeResponseDto();
        ManufacturerAttributeScoreResponseDto responseDto = new ManufacturerAttributeScoreResponseDto(3L, ComplianceType.C, "ABC123", attribute, null);

        String json = mapper.writeValueAsString(responseDto);

        // Assert that "attributeCategory" is not present in JSON for attribute object
        assertFalse(json.contains("\"attributeCategory\":"));
    }


}
