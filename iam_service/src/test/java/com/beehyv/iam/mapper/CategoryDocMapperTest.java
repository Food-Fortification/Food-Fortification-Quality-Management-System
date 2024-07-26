package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.CategoryDocRequestDto;
import com.beehyv.iam.dto.requestDto.DocTypeRequestDto;
import com.beehyv.iam.dto.responseDto.CategoryDocResponseDto;
import com.beehyv.iam.dto.responseDto.DocTypeResponseDto;
import com.beehyv.iam.model.CategoryDoc;
import com.beehyv.iam.model.DocType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryDocMapperTest {

    @Mock
    BaseMapper<DocTypeResponseDto, DocTypeRequestDto, DocType> docTypeMapper;

    @InjectMocks
    CategoryDocMapper categoryDocMapper;

    @Test
    void testToDto() {
        // Mock input data
        CategoryDoc categoryDoc = new CategoryDoc();
        categoryDoc.setId(1L);
        categoryDoc.setIsEnabled(true);
        categoryDoc.setCategoryId(100L);
        categoryDoc.setIsMandatory(false);
        DocType docType = new DocType();
        categoryDoc.setDocType(docType);

        // Call the method under test
        CategoryDocResponseDto categoryDocResponseDto = categoryDocMapper.toDto(categoryDoc);

        // Assertions
        assertNotNull(categoryDocResponseDto);
        assertEquals(1L, categoryDocResponseDto.getId());
        assertTrue(categoryDocResponseDto.getIsEnabled());
        assertEquals(100L, categoryDocResponseDto.getCategoryId());
        assertFalse(categoryDocResponseDto.getIsMandatory());
        assertNotNull(categoryDocResponseDto.getDocType());
        // Additional assertions...
    }

    @Test
    void testToEntity() {
        // Mock input data
        CategoryDocRequestDto categoryDocRequestDto = new CategoryDocRequestDto();
        categoryDocRequestDto.setId(1L);
        categoryDocRequestDto.setIsEnabled(true);
        categoryDocRequestDto.setCategoryId(100L);
        categoryDocRequestDto.setIsMandatory(false);
        categoryDocRequestDto.setDocTypeId(50L);

        // Call the method under test
        CategoryDoc categoryDoc = categoryDocMapper.toEntity(categoryDocRequestDto);

        // Assertions
        assertNotNull(categoryDoc);
        assertEquals(1L, categoryDoc.getId());
        assertTrue(categoryDoc.getIsEnabled());
        assertEquals(100L, categoryDoc.getCategoryId());
        assertFalse(categoryDoc.getIsMandatory());
        assertNotNull(categoryDoc.getDocType());
        assertEquals(50L, categoryDoc.getDocType().getId());
        // Additional assertions...
    }
}
