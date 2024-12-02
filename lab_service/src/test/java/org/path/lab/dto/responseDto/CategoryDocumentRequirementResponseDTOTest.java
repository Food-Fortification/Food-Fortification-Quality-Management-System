package org.path.lab.dto.responseDto;

import org.path.lab.enums.CategoryDocRequirementType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryDocumentRequirementResponseDTOTest {

    @Test
    void testConstructorWithAllFields() {
        // Given
        Long id = 1L;
        Long categoryId = 2L;
        DocTypeResponseDTO docType = new DocTypeResponseDTO(3L, "Document Type");
        Boolean isMandatory = true;
        Boolean isEnabled = false;
        CategoryDocRequirementType categoryDocRequirementType = CategoryDocRequirementType.TEST;

        // When
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(
                id, categoryId, docType, isMandatory, isEnabled, categoryDocRequirementType
        );

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(docType, dto.getDocType());
        assertEquals(isMandatory, dto.getIsMandatory());
        assertEquals(isEnabled, dto.getIsEnabled());
        assertEquals(categoryDocRequirementType, dto.getCategoryDocRequirementType());
    }

    @Test
    void testGetId() {
        // Given
        Long id = 1L;
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        dto.setId(id);

        // When
        Long result = dto.getId();

        // Then
        assertEquals(id, result);
    }

    @Test
    void testSetId() {
        // Given
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        Long id = 1L;

        // When
        dto.setId(id);

        // Then
        assertEquals(id, dto.getId());
    }

    @Test
    void testGetCategoryId() {
        // Given
        Long categoryId = 2L;
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        dto.setCategoryId(categoryId);

        // When
        Long result = dto.getCategoryId();

        // Then
        assertEquals(categoryId, result);
    }

    @Test
    void testSetCategoryId() {
        // Given
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        Long categoryId = 2L;

        // When
        dto.setCategoryId(categoryId);

        // Then
        assertEquals(categoryId, dto.getCategoryId());
    }

    @Test
    void testGetDocType() {
        // Given
        DocTypeResponseDTO docType = new DocTypeResponseDTO(3L, "Document Type");
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        dto.setDocType(docType);

        // When
        DocTypeResponseDTO result = dto.getDocType();

        // Then
        assertEquals(docType, result);
    }

    @Test
    void testSetDocType() {
        // Given
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        DocTypeResponseDTO docType = new DocTypeResponseDTO(3L, "Document Type");

        // When
        dto.setDocType(docType);

        // Then
        assertEquals(docType, dto.getDocType());
    }

    @Test
    void testGetIsMandatory() {
        // Given
        Boolean isMandatory = true;
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        dto.setIsMandatory(isMandatory);

        // When
        Boolean result = dto.getIsMandatory();

        // Then
        assertEquals(isMandatory, result);
    }

    @Test
    void testSetIsMandatory() {
        // Given
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        Boolean isMandatory = true;

        // When
        dto.setIsMandatory(isMandatory);

        // Then
        assertEquals(isMandatory, dto.getIsMandatory());
    }

    @Test
    void testGetIsEnabled() {
        // Given
        Boolean isEnabled = false;
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        dto.setIsEnabled(isEnabled);

        // When
        Boolean result = dto.getIsEnabled();

        // Then
        assertEquals(isEnabled, result);
    }

    @Test
    void testSetIsEnabled() {
        // Given
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        Boolean isEnabled = false;

        // When
        dto.setIsEnabled(isEnabled);

        // Then
        assertEquals(isEnabled, dto.getIsEnabled());
    }

    @Test
    void testGetCategoryDocRequirementType() {
        // Given
        CategoryDocRequirementType type = CategoryDocRequirementType.TEST;
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        dto.setCategoryDocRequirementType(type);

        // When
        CategoryDocRequirementType result = dto.getCategoryDocRequirementType();

        // Then
        assertEquals(type, result);
    }

    @Test
    void testSetCategoryDocRequirementType() {
        // Given
        CategoryDocumentRequirementResponseDTO dto = new CategoryDocumentRequirementResponseDTO(null,null,null,null,null,null);
        CategoryDocRequirementType type = CategoryDocRequirementType.TEST;

        // When
        dto.setCategoryDocRequirementType(type);

        // Then
        assertEquals(type, dto.getCategoryDocRequirementType());
    }
}
