package com.beehyv.fortification.mapperTest;

import com.beehyv.fortification.dto.requestDto.LotPropertyRequestDto;
import com.beehyv.fortification.dto.responseDto.LotPropertyResponseDto;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.LotProperty;
import com.beehyv.fortification.mapper.LotPropertyMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LotPropertyMapperTest {

    @Test
    void testAllMethods() {
        // Arrange
        LotPropertyRequestDto dto = new LotPropertyRequestDto();
        dto.setId(1L);
        dto.setName("testName");
        dto.setValue("testValue");
        dto.setLotId(1L);

        LotProperty entity = new LotProperty();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        entity.setLot(new Lot(dto.getLotId()));

        LotPropertyMapper mapper = new LotPropertyMapper();

        // Act
        LotProperty mappedEntity = mapper.toEntity(dto);
        LotPropertyResponseDto mappedDto = mapper.toDto(entity);

        // Assert
        assertEquals(dto.getId(), mappedEntity.getId());
        assertEquals(dto.getName(), mappedEntity.getName());
        assertEquals(dto.getValue(), mappedEntity.getValue());
        assertEquals(dto.getLotId(), mappedEntity.getLot().getId());

        assertEquals(entity.getId(), mappedDto.getId());
        assertEquals(entity.getName(), mappedDto.getName());
        assertEquals(entity.getValue(), mappedDto.getValue());
    }
}