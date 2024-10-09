package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.FirebaseEvent;
import com.beehyv.Immudb.dto.NotificationUserTokenResponseDto;
import com.beehyv.Immudb.entity.NotificationEvent;
import com.beehyv.Immudb.enums.ActionType;
import com.beehyv.Immudb.enums.EntityType;
import com.beehyv.Immudb.enums.RoleCategoryType;
import com.beehyv.Immudb.manager.NotificationEventManager;
import com.beehyv.Immudb.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private FirebaseMessaging firebaseMessagingMock;

    @Mock
    private NotificationEventManager managerMock;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @Spy
    @InjectMocks
    private NotificationService notificationService;

    private MockedStatic<HttpUtils> mockStatic;
    String jsonString;

    @BeforeEach
    void setUp() {
        mockStatic = mockStatic(HttpUtils.class);
        MockitoAnnotations.openMocks(this);
        jsonString = "[\n" +
                "  {\n" +
                "    \"roleCategoryType\": \"MODULE\",\n" +
                "    \"actionType\": \"action\",\n" +
                "    \"registrationToken\": \"token1\"\n" +
                "  }\n" +
                "]";
        when(HttpUtils.callGetAPI(any(),any())).thenReturn(jsonString);
        notificationService = new NotificationService(firebaseMessagingMock, managerMock);
    }
    @AfterEach
    void tearDown() {
        mockStatic.close();
    }

    @Test
    void testSendNotificationForAllCases() throws FirebaseMessagingException, JsonProcessingException {
        // Arrange
        FirebaseEvent firebaseEvent = new FirebaseEvent();
        firebaseEvent.setEntityNo("123");
        firebaseEvent.setPreviousStateDisplayName("PREMIX");
        firebaseEvent.setCurrentStateDisplayName("FRK");
        firebaseEvent.setEntity(EntityType.batch);
        firebaseEvent.setId(1L);
        firebaseEvent.setCategoryId(1L);
        firebaseEvent.setCategoryName("Category 1");
        firebaseEvent.setIsIndependentBatch(true);

        NotificationUserTokenResponseDto tokenDto = new NotificationUserTokenResponseDto();
        tokenDto.setRegistrationToken("token1");
        tokenDto.setRoleCategoryType(RoleCategoryType.MODULE);
        tokenDto.setActionType(ActionType.action);
        List<NotificationUserTokenResponseDto> tokenDtoList = new ArrayList<>();
        tokenDtoList.add(tokenDto);
        NotificationEvent notificationEvent = new NotificationEvent();

        when(managerMock.findByEntityIdAndState(any(), any())).thenReturn(notificationEvent);
//        when(notificationService.getTokens(firebaseEvent)).thenReturn(tokenDtoList);

        // Act & Assert
        testSendNotificationForCase(firebaseEvent, "sentBatchSampleToLabTest", "Sample sent to lab", "Batch 123 has been sent for lab test");
        testSendNotificationForCase(firebaseEvent, "batchSampleInLab", "Sample received", "Batch 123 has been received in Lab");
        testSendNotificationForCase(firebaseEvent, "batchSampleLabTestDone", "Test done", "Batch 123 test has been done in lab");
        testSendNotificationForCase(firebaseEvent, "rejected", "Batch rejected", "Batch 123 has been rejected after test has failed");
        testSendNotificationForCase(firebaseEvent, "batchToDispatch", "Batch approved", "Batch 123 has been approved and ready to be dispatched");
        testSendNotificationForCase(firebaseEvent, "created", "Batch created", "Batch 123 has been created");
        testSendNotificationForCase(firebaseEvent, "batchSampleRejected", "Batch sample rejected", "Batch 123 has been rejected by lab");
        testSendNotificationForCase(firebaseEvent, "lotSampleRejected", "Lot sample rejected", "Lot 123 has been  rejected");
        testSendNotificationForCase(firebaseEvent, "receivedRejected", "Rejected lot received", "Rejected lot 123 has been received");
        testSendNotificationForCase(firebaseEvent, "sentBackRejected", "Lot sent back", "Lot 123 has been sent back after rejected");
        testSendNotificationForCase(firebaseEvent, "toSendBackRejected", "Lot to send back", "Lot 123 test has been rejected and ready to send back");
        testSendNotificationForCase(firebaseEvent, "approved", "Lot approved", "Lot 123 has been approved");
        testSendNotificationForCase(firebaseEvent, "lotSampleLabTestDone", "Test done", "Lot 123 test has been done");
        testSendNotificationForCase(firebaseEvent, "lotSampleInLab", "Sample received", "Lot 123 has been received in lab");
        testSendNotificationForCase(firebaseEvent, "sentLotSampleToLabTest", "Sample sent to lab", "Lot 123 has been sent for lab test");
        testSendNotificationForCase(firebaseEvent, "lotReceived", "Lot received", "Dispatched lot 123 has been received");
        testSendNotificationForCase(firebaseEvent, "dispatched", "Lot dispatched", "Lot 123 has been dispatched");
        testSendNotificationForCase(firebaseEvent, "unknown", "Rice Fortification", "  ");
    }

    private void testSendNotificationForCase(FirebaseEvent firebaseEvent, String currentStateName, String expectedTitle, String expectedBody) throws FirebaseMessagingException, JsonProcessingException {
        firebaseEvent.setCurrentStateName(currentStateName);

        // Act
        notificationService.sendNotification(firebaseEvent);

        // Assert
        verify(firebaseMessagingMock, atLeast(0)).send(messageCaptor.capture());
        Message message = messageCaptor.getValue();
        assertNotNull(message);
    }
}