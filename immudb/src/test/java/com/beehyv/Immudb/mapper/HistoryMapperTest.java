package com.beehyv.Immudb.mapper;

import com.beehyv.Immudb.dto.HistoryResponseDto;
import com.beehyv.Immudb.entity.BatchEventEntity;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryMapperTest {

    @Test
    public void testMapHistoryEntityToDto() {
        // Arrange
        BatchEventEntity event = new BatchEventEntity();
        event.setId(1L);
        event.setManufacturerName("ManufacturerName");
        event.setManufacturerAddress("ManufacturerAddress");
        event.setEntityId("2L");
        event.setType("Type");
        event.setState("State");
        event.setDateOfAction(Timestamp.valueOf(LocalDateTime.now()));
        event.setComments("Comments");
        event.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        event.setCreatedBy("CreatedBy");

        HistoryMapper mapper = new HistoryMapper();

        // Act
        HistoryResponseDto dto = mapper.mapHistoryEntityToDto(event);

        // Assert
        assertEquals(event.getId(), dto.getId());
        assertEquals(event.getManufacturerName(), dto.getManufacturerName());
        assertEquals(event.getManufacturerAddress(), dto.getManufacturerAddress());
        assertEquals(event.getEntityId(), dto.getEntityId());
        assertEquals(event.getType(), dto.getType());
        assertEquals(event.getState(), dto.getState());
        assertEquals(event.getDateOfAction(), dto.getDateOfAction());
        assertEquals(event.getComments(), dto.getComments());
        assertEquals(event.getCreatedDate(), dto.getCreatedDate());
        assertEquals(event.getCreatedBy(), dto.getCreatedBy());
    }
}