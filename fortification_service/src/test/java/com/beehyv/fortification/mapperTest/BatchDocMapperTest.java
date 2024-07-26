package com.beehyv.fortification.mapperTest;

import com.beehyv.fortification.dto.requestDto.BatchDocRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchDocResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.mapper.BatchDocMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BatchDocMapperTest {

    @Test
    void testAllMethods() {
        // Arrange
        BatchDocRequestDto dto = new BatchDocRequestDto();
        dto.setId(1L);
        dto.setPath("testPath");
        dto.setName("testName");
        dto.setExpiry(new Date());
        dto.setCategoryDocId(1L);
        dto.setBatchId(1L);

        BatchDoc entity = new BatchDoc();
        entity.setId(dto.getId());
        entity.setPath(dto.getPath());
        entity.setName(dto.getName());
        entity.setExpiry(dto.getExpiry());
        entity.setCategoryDoc(new CategoryDoc(dto.getCategoryDocId()));
        entity.setBatch(new Batch(dto.getBatchId()));
        entity.setCategoryDoc(new CategoryDoc(1L, new Category(), new DocType(), true, true));

        BatchDocMapper mapper = new BatchDocMapper();

        // Act
        BatchDoc mappedEntity = mapper.toEntity(dto);
        BatchDocResponseDto mappedDto = mapper.toDto(entity);

        // Assert
        assertEquals(dto.getId(), mappedEntity.getId());
        assertEquals(dto.getPath(), mappedEntity.getPath());
        assertEquals(dto.getName(), mappedEntity.getName());
        assertEquals(dto.getExpiry(), mappedEntity.getExpiry());
        assertEquals(dto.getCategoryDocId(), mappedEntity.getCategoryDoc().getId());
        assertEquals(dto.getBatchId(), mappedEntity.getBatch().getId());

        assertEquals(entity.getId(), mappedDto.getId());
        assertEquals(entity.getPath(), mappedDto.getPath());
        assertEquals(entity.getName(), mappedDto.getName());
        assertEquals(entity.getExpiry(), mappedDto.getExpiry());
        assertEquals(entity.getCategoryDoc().getId(), mappedDto.getCategoryDoc().getId());
    }
}