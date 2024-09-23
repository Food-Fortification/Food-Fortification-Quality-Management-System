package com.beehyv.Immudb.entity;

import com.beehyv.Immudb.enums.EntityType;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationEventTest {

    @Test
    public void testNotificationEventFields() {
        // Arrange
        NotificationEvent event = new NotificationEvent();
        event.setId(1L);
        event.setEntityType(EntityType.batch); // replace with actual EntityType
        event.setEntityId(2L);
        event.setEntityNo("EntityNo");
        event.setManufacturerId(3L);
        event.setTargetManufacturerId(4L);
        event.setCategoryId(5L);
        event.setCategoryName("CategoryName");
        event.setLabId(6L);
        event.setDateOfAction(new Date());
        event.setNotificationDate(LocalDateTime.now());
        event.setNotificationTitle("NotificationTitle");
        event.setIsIndependentBatch(true);
        event.setCurrentStateName("CurrentStateName");
        event.setPreviousStateName("PreviousStateName");
        event.setIsTargetManufacturer(true);
        event.setCurrentStateDisplayName("CurrentStateDisplayName");
        event.setPreviousStateDisplayName("PreviousStateDisplayName");
        event.setLabSampleId(7L);

        // Assert
        assertThat(event.getId()).isEqualTo(1L);
        assertThat(event.getEntityType()).isEqualTo(EntityType.batch); // replace with actual EntityType
        assertThat(event.getEntityId()).isEqualTo(2L);
        assertThat(event.getEntityNo()).isEqualTo("EntityNo");
        assertThat(event.getManufacturerId()).isEqualTo(3L);
        assertThat(event.getTargetManufacturerId()).isEqualTo(4L);
        assertThat(event.getCategoryId()).isEqualTo(5L);
        assertThat(event.getCategoryName()).isEqualTo("CategoryName");
        assertThat(event.getLabId()).isEqualTo(6L);
        assertThat(event.getDateOfAction()).isToday();
        assertThat(event.getNotificationTitle()).isEqualTo("NotificationTitle");
        assertThat(event.getIsIndependentBatch()).isTrue();
        assertThat(event.getCurrentStateName()).isEqualTo("CurrentStateName");
        assertThat(event.getPreviousStateName()).isEqualTo("PreviousStateName");
        assertThat(event.getIsTargetManufacturer()).isTrue();
        assertThat(event.getCurrentStateDisplayName()).isEqualTo("CurrentStateDisplayName");
        assertThat(event.getPreviousStateDisplayName()).isEqualTo("PreviousStateDisplayName");
        assertThat(event.getLabSampleId()).isEqualTo(7L);
    }
}