package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.ManufacturerDocsRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerDocsResponseDto;
import com.beehyv.iam.model.CategoryDoc;
import com.beehyv.iam.model.ManufacturerDoc;
import com.beehyv.parent.exceptions.CustomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerDocsMapperTest {

    @Test
    void testToDto() {
        // Prepare test data
        ManufacturerDoc manufacturerDoc = new ManufacturerDoc();
        manufacturerDoc.setId(1L);
        manufacturerDoc.setDocName("Test Doc");
        manufacturerDoc.setDocPath("/path/to/doc");
        CategoryDoc categoryDoc = new CategoryDoc();
        categoryDoc.setId(2L);
        manufacturerDoc.setCategoryDoc(categoryDoc);

        // Create the mapper
        ManufacturerDocsMapper manufacturerDocsMapper = new ManufacturerDocsMapper();

        // Map the entity to DTO
        ManufacturerDocsResponseDto manufacturerDocsResponseDto = manufacturerDocsMapper.toDto(manufacturerDoc);

        // Assertions
        assertNotNull(manufacturerDocsResponseDto);
        assertEquals(1L, manufacturerDocsResponseDto.getId());
        assertEquals("Test Doc", manufacturerDocsResponseDto.getDocName());
        assertEquals("/path/to/doc", manufacturerDocsResponseDto.getDocPath());
        assertNotNull(manufacturerDocsResponseDto.getCategoryDoc());
        assertEquals(2L, manufacturerDocsResponseDto.getCategoryDoc().getId());
    }

    @Test
    public void testToEntity_withInvalidFileName_shouldThrowCustomException() {
        // Create a ManufacturerDocsRequestDto with an invalid file name
        ManufacturerDocsRequestDto dto = new ManufacturerDocsRequestDto();
        dto.setDocPath("invalid/file/name");

        // Create a ManufacturerDocsMapper instance
        ManufacturerDocsMapper mapper = new ManufacturerDocsMapper();

        // Test that the toEntity method throws a CustomException with status 400
        assertThrows(CustomException.class, () -> {
            mapper.toEntity(dto);
        });
    }

}
