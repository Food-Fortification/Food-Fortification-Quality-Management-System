package com.beehyv.Immudb.mapper;

import com.beehyv.Immudb.dto.NotificationResponseDto;
import com.beehyv.Immudb.entity.NotificationEvent;
import com.beehyv.Immudb.enums.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationMapperTest {

    @Test
    public void testMapNotificationEntityToDto() {

        NotificationEvent event = new NotificationEvent();
        event.setCurrentStateDisplayName("currentStateDisplayName");
        event.setNotificationTitle("notificationTitle");
        event.setEntityType(EntityType.batch);
        event.setEntityId(1L);
        event.setCategoryName("categoryName");
        event.setCategoryId(1L);
        event.setEntityNo("entityNo");
        event.setPreviousStateDisplayName("previousStateDisplayName");
        event.setIsIndependentBatch(true);
        event.setLabSampleId(1L);

        NotificationResponseDto dto = NotificationMapper.mapNotificationEntityToDto(event);

        assertEquals(event.getCurrentStateDisplayName(), dto.getCurrentStateDisplayName());
        assertEquals(event.getNotificationTitle(), dto.getNotificationTitle());
        assertEquals(event.getEntityType(), dto.getEntityType());
        assertEquals(event.getEntityId(), dto.getEntityId());
        assertEquals(event.getCategoryName(), dto.getCategoryName());
        assertEquals(event.getCategoryId(), dto.getCategoryId());
        assertEquals(event.getEntityNo(), dto.getEntityNo());
        assertEquals(event.getPreviousStateDisplayName(), dto.getPreviousStateDisplayName());
        assertEquals(event.getIsIndependentBatch(), dto.getIsIndependentBatch());
        assertEquals(event.getNotificationDate(), dto.getNotificationDate());
        assertEquals(event.getLabSampleId(), dto.getLabSampleId());
    }
}