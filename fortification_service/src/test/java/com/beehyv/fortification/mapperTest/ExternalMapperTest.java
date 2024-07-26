package com.beehyv.fortification.mapperTest;

import com.beehyv.fortification.dto.external.BatchExternalRequestDto;
import com.beehyv.fortification.dto.requestDto.LotRequestDto;
import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.mapper.ExternalMapper;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalMapperTest {

    @Test
    void testAllMethods() {
        // Arrange
        BatchExternalRequestDto dto = new BatchExternalRequestDto();
        dto.setDateOfManufacture(new Date());
        dto.setTotalQuantity(100.0);
        dto.setComments("Test comments");
        dto.setDateOfAcceptance(new Date());

        Category category = new Category();
        category.setId(1L);

        Long manufacturerId = 1L;
        Long targetManufacturerId = 2L;

        SizeUnitRequestDto sizeUnitRequestDto = new SizeUnitRequestDto();
        sizeUnitRequestDto.setSize(10L);
        sizeUnitRequestDto.setUomId(1L);

        // Act
        Batch batchEntity = ExternalMapper.mapBatchDtoToEntityExternal(dto, category, manufacturerId);
        Lot lotEntity = ExternalMapper.mapLotDtoToEntityExternal(dto, batchEntity, targetManufacturerId);
        LotRequestDto lotRequestDto = ExternalMapper.mapLotExternalDtoToRequestDto(batchEntity, targetManufacturerId, sizeUnitRequestDto);

        // Assert
        assertEquals(dto.getTotalQuantity(), batchEntity.getTotalQuantity());
        assertEquals(dto.getTotalQuantity(), lotEntity.getTotalQuantity());
        assertEquals(batchEntity.getId(), lotRequestDto.getBatchId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getDateOfManufacture());
        calendar.add(Calendar.YEAR, 2);
        Date updatedDoe = calendar.getTime();

        assertEquals(updatedDoe, batchEntity.getDateOfExpiry());
        assertEquals(dto.getDateOfAcceptance(), lotEntity.getDateOfReceiving());
        assertEquals(dto.getDateOfAcceptance(), lotEntity.getDateOfAcceptance());
        assertEquals(sizeUnitRequestDto.getSize(), lotRequestDto.getSizeUnits().iterator().next().getSize());
    }
}