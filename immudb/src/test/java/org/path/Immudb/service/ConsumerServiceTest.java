package org.path.Immudb.service;

import org.path.Immudb.dto.FirebaseEvent;
import org.path.Immudb.entity.BatchEventEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ConsumerServiceTest {

    @Mock
    private ImmudbService immudbService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ConsumerService consumerService;

    String message;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testConsumeMessage() throws Exception {
        message = "{"
                + "\"entityId\":\"entity1\","
                + "\"id\":1,"
                + "\"entityId\":\"entity1\","
                + "\"manufacturerName\":\"manufacturer1\","
                + "\"manufacturerAddress\":\"address1\","
                + "\"manufacturerName\":\"manufacturer1\","
                + "\"type\":\"type1\","
                + "\"state\":\"state1\","
                + "\"manufacturerAddress\":\"address1\","
                + "\"type\":\"type1\","
                + "\"comments\":\"comments1\","
                + "\"state\":\"state1\","
                + "\"dateOfAction\":\"2022-01-01T00:00:00.000+00:00\","
                + "\"createdBy\":\"creator1\","
                + "\"comments\":\"comments1\","
                + "\"createdDate\":\"2022-01-01T00:00:00.000+00:00\""
                + "}";

        BatchEventEntity batchEvent = new ObjectMapper().readValue(message, BatchEventEntity.class);

        doNothing().when(immudbService).put(any(BatchEventEntity.class), eq(BatchEventEntity.class));


        //exception case
        message="";
        String finalMessage = message;
        assertThrows(Exception.class, ()->consumerService.consumeMessage(finalMessage));

    }

    @Test
    public void testConsumeNotification() throws Exception {
        message = "{"
                + "\"id\":1,"
                + "\"categoryName\":\"category1\","
                + "\"entity\":\"lot\","
                + "\"targetManufacturerId\":1,"
                + "\"manufacturerId\":1,"
                + "\"labId\":\"lab1\","
                + "\"categoryId\":1,"
                + "\"categoryName\":\"category1\","
                + "\"manufacturerId\":1,"
                + "\"labId\":\"lab1\","
                + "\"dateOfAction\":\"2022-01-01T00:00:00.000+00:00\","
                + "\"dateOfAction\":\"2022-01-01T00:00:00.000+00:00\","
                + "\"notificationDate\":\"2022-01-01T00:00:00.000\","
                + "\"entityNo\":\"entity1\","
                + "\"isIndependentBatch\":true,"
                + "\"notificationDate\":\"2022-01-01T00:00:00.000\","
                + "\"entityNo\":\"entity1\","
                + "\"currentStateName\":\"state1\","
                + "\"previousStateName\":\"state2\","
                + "\"isIndependentBatch\":true,"
                + "\"currentStateName\":\"state1\","
                + "\"currentStateDisplayName\":\"state1\","
                + "\"previousStateName\":\"state2\","
                + "\"previousStateDisplayName\":\"state2\","
                + "\"labSampleId\":1"
                + "}";
        FirebaseEvent firebaseEvent = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(message, FirebaseEvent.class);

        doNothing().when(notificationService).sendNotification(any(FirebaseEvent.class));

        consumerService.consumeNotification(message);

        verify(notificationService, times(1)).sendNotification(any(FirebaseEvent.class));
    }
}