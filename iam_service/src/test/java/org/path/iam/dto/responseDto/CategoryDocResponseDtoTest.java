package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDocResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        Long categoryId = 2L;
        DocTypeResponseDto docType = new DocTypeResponseDto(3L, "Aadhar Card");
        boolean isMandatory = true;
        boolean isEnabled = false;

        CategoryDocResponseDto responseDto = new CategoryDocResponseDto(id, categoryId, docType, isMandatory, isEnabled);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(categoryId, responseDto.getCategoryId());
        assertEquals(docType, responseDto.getDocType());
        assertEquals(isMandatory, responseDto.getIsMandatory());
        assertEquals(isEnabled, responseDto.getIsEnabled());
    }

    @Test
    public void testWithOptionalFields() {
        Long categoryId = 5L;
        DocTypeResponseDto docType = null;
        boolean isMandatory = false;
        CategoryDocResponseDto responseDto = new CategoryDocResponseDto(null, categoryId, docType, isMandatory, null);

        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertEquals(categoryId, responseDto.getCategoryId());
        assertNull(responseDto.getDocType());
        assertEquals(isMandatory, responseDto.getIsMandatory());
        assertNull(responseDto.getIsEnabled());
    }
}
