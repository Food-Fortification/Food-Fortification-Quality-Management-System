package org.path.fortification.mapperTest;

import org.path.fortification.dto.responseDto.MixMappingMonitorResponseDto;
import org.path.fortification.entity.MixMapping;
import org.path.fortification.entity.UOM;
import org.path.fortification.mapper.MixMappingMonitorMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixMappingMonitorMapperTest {

    @Test
    void testToListDto() {
        // Arrange
        MixMapping entity = new MixMapping();
        entity.setId(1L);
        entity.setQuantityUsed(100.0);
        UOM uom = new UOM();
        uom.setId(1L);
        uom.setName("KG");
        entity.setUom(uom);

        MixMappingMonitorMapper mapper = new MixMappingMonitorMapper();

        // Act
        MixMappingMonitorResponseDto mappedDto = mapper.toListDto(entity);

        // Assert
        assertEquals(entity.getId(), mappedDto.getId());
        assertEquals(entity.getQuantityUsed(), mappedDto.getQuantityUsed());
        assertEquals(entity.getUom().getId(), mappedDto.getUom().getId());
        assertEquals(entity.getUom().getName(), mappedDto.getUom().getName());
    }
}