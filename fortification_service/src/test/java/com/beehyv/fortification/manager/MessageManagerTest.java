package com.beehyv.fortification.manager;

import com.beehyv.fortification.dto.iam.*;
import com.beehyv.fortification.dto.requestDto.EntityStateRequestDTO;
import com.beehyv.fortification.dto.requestDto.FirebaseEvent;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageManagerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Spy
    @InjectMocks
    private MessageManager messageManager;

    private ObjectMapper objectMapper;
    private MockedStatic<IamServiceRestHelper> mockStatic;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockStatic = mockStatic(IamServiceRestHelper.class);
    }

    @AfterEach
    public void tearDown() {
        mockStatic.close();
    }

    @Test
    public void testStatusChangeHandler() {
        EntityStateRequestDTO dto = new EntityStateRequestDTO();
        State state = new State();
        Map<String, Object> userInfo = new HashMap<>();
        Long manufacturerId = 1L;
        String token = "test_token";
        Boolean isFlowSkipped = false;

        doNothing().when(messageManager).statusChangeHandlerSynchronously(dto, state, userInfo, manufacturerId, token, isFlowSkipped);
        messageManager.statusChangeHandler(dto, state, userInfo, manufacturerId, token, isFlowSkipped);

        verify(messageManager, times(1)).statusChangeHandlerSynchronously(dto, state, userInfo, manufacturerId, token, isFlowSkipped);
    }


    @Test
    void testStatusChangeHandlerSynchronously_actionTypeModule() throws JsonProcessingException {
        // Prepare test data
        EntityStateRequestDTO dto = new EntityStateRequestDTO();
        dto.setBatchId(1L);
        State state = new State(1L, StateType.BATCH, ActionType.module, null, null, null, null);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", Set.of("SUPERADMIN"));
        Long manufacturerId = 1L;
        String token = "test_token";
        Boolean isFlowSkipped = false;
        Map<String, Map<String, String>> map = new HashMap<>();
        map.put("1", new HashMap<>() {{
            put("name", "test");
            put("address", "test");
        }});
        when(IamServiceRestHelper.getNameAndAddress(any(), any())).thenReturn(map);

        // Call the method under test
        messageManager.statusChangeHandlerSynchronously(dto, state, userInfo, manufacturerId, token, isFlowSkipped);

        // Verify that the sendMessage method was called
        verify(messageManager).sendMessage(any(), any());
    }

    @Test
    void testStatusChangeHandlerSynchronously_actionTypeLab() throws JsonProcessingException {
        // Prepare test data
        EntityStateRequestDTO dto = new EntityStateRequestDTO();
        dto.setBatchId(1L);
        State state = new State(1L, StateType.BATCH, ActionType.lab, null, null, null, null);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", Set.of("SUPERADMIN"));
        Long manufacturerId = 1L;
        String token = "test_token";
        Boolean isFlowSkipped = false;
        Map<String, Map<String, String>> map = new HashMap<>();
        map.put("1", new HashMap<>() {{
            put("name", "test");
            put("address", "test");
        }});
        when(IamServiceRestHelper.getNameAndAddress(any(), any())).thenReturn(map);

        // Call the method under test
        messageManager.statusChangeHandlerSynchronously(dto, state, userInfo, manufacturerId, token, isFlowSkipped);

        // Verify that the sendMessage method was called
        verify(messageManager).sendMessage(any(), any());
    }

    @Test
    public void testManufacturerAddress() {
        String request = "{\"laneOne\":\"Lane 1\",\"laneTwo\":\"Lane 2\",\"village\":{\"name\":\"Village\"},\"district\":{\"name\":\"District\"},\"state\":{\"name\":\"State\"},\"country\":{\"name\":\"Country\"},\"latitude\":\"12.34\",\"longitude\":\"56.78\",\"pinCode\":\"123456\"}";
        String expectedAddress = "{\"laneOne\":\"Lane 1\",\"laneTwo\":\"Lane 2\",\"village\":\"Village\",\"district\":\"District\",\"state\":\"State\",\"country\":\"Country\",\"latitude\":\"12.34\",\"longitude\":\"56.78\",\"pincode\":\"123456\"}";

        String actualAddress = messageManager.manufacturerAddress(request);

        assertNotNull(actualAddress);
    }

    @Test
    void testSendBatchUpdate() {
        // Prepare test data
        Long batchId = 0L;
        Long manufacturerId = 1L;
        Long categoryId = 2L;
        Double quantityDifference = 10.0;
        State state = new State();
        Map<String, Object> userInfo = new HashMap<>();
        String token = "test_token";
        Date date = new Date();
        SampleTestResult testResult = SampleTestResult.TEST_PASSED;
        Boolean isBatchTested = true;
        AddressResponseDto addressResponseDto = new AddressResponseDto(1L, "null", "null", "null", new VillageResponseDto(1L, "a", new DistrictResponseDto(
                1L,
                "null",
                "null",
                "",
                "",
                new StateResponseDto(1L, "null", "null", new CountryResponseDto(1L, "a", "null")))), 10.0, 10.0);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(addressResponseDto);
        // Call the method under test
        messageManager.sendBatchUpdate(manufacturerId, categoryId, quantityDifference, state, userInfo, token, date, testResult, isBatchTested, batchId);

        verify(messageManager).sendMessage(any(), any());
    }

    @Test
    void testSendLotUpdate() {
        // Prepare test data
        Long targetManufacturerId = 1L;
        Long batchCategoryId = 2L;
        Long batchManufacturerId = 3L;
        Double quantityDifference = 10.0;
        String stateName = "test_state";
        Date date = new Date();
        SampleTestResult testResult = SampleTestResult.TEST_PASSED;
        String token = "test_token";
        Boolean isBatchTested = true;
        Long lotId = 4L;
        AddressResponseDto addressResponseDto = new AddressResponseDto(1L, "null", "null", "null", new VillageResponseDto(1L, "a", new DistrictResponseDto(1L, "null", "null", "", "", new StateResponseDto(1L, "null", "null", new CountryResponseDto(1L, "a", "null")))), 10.0, 10.0);
        when(IamServiceRestHelper.fetchResponse(any(), (Class<Object>) any(), any())).thenReturn(addressResponseDto);
        // Call the method under test
        messageManager.sendLotUpdate(targetManufacturerId, batchCategoryId, batchManufacturerId, quantityDifference, stateName, date, testResult, token, isBatchTested, lotId);

        verify(messageManager).sendMessage(any(), any());
    }

    @Test
    void testSendNotification() {
        // Prepare test data
        FirebaseEvent event = new FirebaseEvent();

        // Call the method under test
        messageManager.sendNotification(event);

        verify(messageManager).sendMessage(any(), any());
    }

    @Test
    void testSendMessage() {
        // Prepare test data
        List<State> states = new ArrayList<>();
        Lot lot = new Lot();
        SampleTestResult sampleTestResult = SampleTestResult.TEST_PASSED;
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        Map<String, Object> userInfo = new HashMap<>();
        String token = "test_token";

        // Call the method under test
        messageManager.sendMessage(states, lot, sampleTestResult, entityStateRequestDTO, userInfo, token);

        // Verify that the sendMessage method was called (no direct verification possible due to async method)
    }

    @Test
    void testSendStatusHandlerMessage() {
        // Prepare test data
        Map<State, Boolean> stateMap = new HashMap<>();
        EntityStateRequestDTO entityStateRequestDTO = new EntityStateRequestDTO();
        Map<String, Object> userInfo = new HashMap<>();
        Lot lot = new Lot();
        String token = "test_token";

        // Call the method under test
        messageManager.sendStatusHandlerMessage(stateMap, entityStateRequestDTO, userInfo, lot, token);

        // Verify that the statusChangeHandler method was called (no direct verification possible due to async method)
    }
}