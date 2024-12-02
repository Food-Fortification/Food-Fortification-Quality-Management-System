package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerDocsRequestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        ManufacturerDocsRequestDto dto = new ManufacturerDocsRequestDto();
        assertNull(dto.getId());
        assertNull(dto.getManufacturerId());
        assertNull(dto.getCategoryDocId());
        assertNull(dto.getDocName());
        assertNull(dto.getDocPath());
        assertNull(dto.getDocExpiry());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryDocId = 3L;
        String docName = "Document Name";
        String docPath = "/path/to/doc";
        LocalDate docExpiry = LocalDate.now();

        ManufacturerDocsRequestDto dto = new ManufacturerDocsRequestDto(id, manufacturerId, categoryDocId, docName, docPath, docExpiry);

        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(categoryDocId, dto.getCategoryDocId());
        assertEquals(docName, dto.getDocName());
        assertEquals(docPath, dto.getDocPath());
        assertEquals(docExpiry, dto.getDocExpiry());
    }

    @Test
    public void testSettersAndGetters() {
        ManufacturerDocsRequestDto dto = new ManufacturerDocsRequestDto();
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryDocId = 3L;
        String docName = "Document Name";
        String docPath = "/path/to/doc";
        LocalDate docExpiry = LocalDate.now();

        dto.setId(id);
        dto.setManufacturerId(manufacturerId);
        dto.setCategoryDocId(categoryDocId);
        dto.setDocName(docName);
        dto.setDocPath(docPath);
        dto.setDocExpiry(docExpiry);

        assertEquals(id, dto.getId());
        assertEquals(manufacturerId, dto.getManufacturerId());
        assertEquals(categoryDocId, dto.getCategoryDocId());
        assertEquals(docName, dto.getDocName());
        assertEquals(docPath, dto.getDocPath());
        assertEquals(docExpiry, dto.getDocExpiry());
    }

    @Test
    public void testEqualsAndHashCode() {
        Long id = 1L;
        Long manufacturerId = 2L;
        Long categoryDocId = 3L;
        String docName = "Document Name";
        String docPath = "/path/to/doc";
        LocalDate docExpiry = LocalDate.now();

        ManufacturerDocsRequestDto dto1 = new ManufacturerDocsRequestDto(id, manufacturerId, categoryDocId, docName, docPath, docExpiry);
        ManufacturerDocsRequestDto dto2 = new ManufacturerDocsRequestDto(id, manufacturerId, categoryDocId, docName, docPath, docExpiry);
        ManufacturerDocsRequestDto dto3 = new ManufacturerDocsRequestDto();

        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
