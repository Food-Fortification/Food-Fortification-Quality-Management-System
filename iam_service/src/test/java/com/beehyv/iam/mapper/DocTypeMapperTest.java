package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.DocTypeRequestDto;
import com.beehyv.iam.dto.responseDto.DocTypeResponseDto;
import com.beehyv.iam.model.DocType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DocTypeMapperTest {

    @Test
    void testToDto() {
        // Prepare test data
        DocType docType = new DocType();
        docType.setId(1L);
        docType.setName("Test DocType");

        // Create the mapper
        DocTypeMapper docTypeMapper = new DocTypeMapper();

        // Map the entity to DTO
        DocTypeResponseDto docTypeResponseDto = docTypeMapper.toDto(docType);

        // Assertions
        assertNotNull(docTypeResponseDto);
        assertEquals(1L, docTypeResponseDto.getId());
        assertEquals("Test DocType", docTypeResponseDto.getName());
    }

    @Test
    void testToEntity() {
        // Prepare test data
        DocTypeRequestDto docTypeRequestDto = new DocTypeRequestDto();
        docTypeRequestDto.setId(1L);
        docTypeRequestDto.setName("Test DocType");

        // Create the mapper
        DocTypeMapper docTypeMapper = new DocTypeMapper();

        // Map the DTO to entity
        DocType docType = docTypeMapper.toEntity(docTypeRequestDto);

        // Assertions
        assertNotNull(docType);
        assertEquals(1L, docType.getId());
        assertEquals("Test DocType", docType.getName());
    }
}
