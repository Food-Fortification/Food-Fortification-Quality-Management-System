package org.path.iam.dto.requestDto;

import org.path.iam.enums.ComplianceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManufacturerAttributeScoreRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        Long attributeId = 2L;
        ComplianceType compliance = ComplianceType.C;
        String value = "Some value";
        Long attributeCategoryScoreId = 3L;

        ManufacturerAttributeScoreRequestDto requestDto = new ManufacturerAttributeScoreRequestDto(
                id, attributeId, compliance, value, attributeCategoryScoreId);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(attributeId, requestDto.getAttributeId());
        assertEquals(compliance, requestDto.getCompliance());
        assertEquals(value, requestDto.getValue());
        assertEquals(attributeCategoryScoreId, requestDto.getAttributeCategoryScoreId());
    }

    @Test
    public void testSetters() {
        ManufacturerAttributeScoreRequestDto requestDto = new ManufacturerAttributeScoreRequestDto();

        Long newAttributeId = 4L;
        ComplianceType newCompliance = ComplianceType.C;
        String newValue = "Updated value";
        Long newAttributeCategoryScoreId = 5L;

        requestDto.setAttributeId(newAttributeId);
        requestDto.setCompliance(newCompliance);
        requestDto.setValue(newValue);
        requestDto.setAttributeCategoryScoreId(newAttributeCategoryScoreId);

        assertEquals(newAttributeId, requestDto.getAttributeId());
        assertEquals(newCompliance, requestDto.getCompliance());
        assertEquals(newValue, requestDto.getValue());
        assertEquals(newAttributeCategoryScoreId, requestDto.getAttributeCategoryScoreId());
    }
}