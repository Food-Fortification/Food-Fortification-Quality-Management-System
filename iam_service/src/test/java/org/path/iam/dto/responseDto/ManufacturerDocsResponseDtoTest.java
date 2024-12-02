package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ManufacturerDocsResponseDtoTest {

    private ManufacturerDocsResponseDto manufacturerDocsResponseDto;
    private CategoryDocResponseDto categoryDocResponseDto;

    @BeforeEach
    public void setUp() {
        categoryDocResponseDto = new CategoryDocResponseDto();
        categoryDocResponseDto.setId(1L);

        manufacturerDocsResponseDto = new ManufacturerDocsResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        ManufacturerDocsResponseDto dto = new ManufacturerDocsResponseDto();
        assertNull(dto.getId());
        assertNull(dto.getCategoryDoc());
        assertNull(dto.getDocName());
        assertNull(dto.getDocPath());
        assertNull(dto.getDocExpiry());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDate docExpiry = LocalDate.of(2023, 12, 31);
        ManufacturerDocsResponseDto dto = new ManufacturerDocsResponseDto(1L, categoryDocResponseDto, "Test Doc", "path/to/doc", docExpiry);

        assertEquals(1L, dto.getId());
        assertEquals(categoryDocResponseDto, dto.getCategoryDoc());
        assertEquals("Test Doc", dto.getDocName());
        assertEquals("path/to/doc", dto.getDocPath());
        assertEquals(docExpiry, dto.getDocExpiry());
    }

    @Test
    public void testSettersAndGetters() {
        LocalDate docExpiry = LocalDate.of(2023, 12, 31);

        manufacturerDocsResponseDto.setId(1L);
        manufacturerDocsResponseDto.setCategoryDoc(categoryDocResponseDto);
        manufacturerDocsResponseDto.setDocName("Test Doc");
        manufacturerDocsResponseDto.setDocPath("path/to/doc");
        manufacturerDocsResponseDto.setDocExpiry(docExpiry);

        assertEquals(1L, manufacturerDocsResponseDto.getId());
        assertEquals(categoryDocResponseDto, manufacturerDocsResponseDto.getCategoryDoc());
        assertEquals("Test Doc", manufacturerDocsResponseDto.getDocName());
        assertEquals("path/to/doc", manufacturerDocsResponseDto.getDocPath());
        assertEquals(docExpiry, manufacturerDocsResponseDto.getDocExpiry());
    }
}
