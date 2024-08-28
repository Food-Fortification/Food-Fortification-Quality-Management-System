package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.HistoryRequestDto;
import com.beehyv.Immudb.dto.HistoryResponseDto;
import com.beehyv.Immudb.entity.BatchEventEntity;
import com.beehyv.Immudb.mapper.HistoryMapper;
import com.beehyv.Immudb.utils.HttpUtils;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import io.codenotary.immudb4j.ImmuClient;
import io.codenotary.immudb4j.sql.SQLQueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImmudbServiceImplTest {

    @Spy
    @InjectMocks
    private ImmudbServiceImpl immudbService;

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private KeycloakInfo mockKeycloakInfo;

    @Mock
    private HistoryMapper mockHistoryMapper;

    @Mock
    private ImmuClient mockClient;
    MockedStatic<HttpUtils> mockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testVerifyConnection() throws Exception {
        when(mockConnection.isClosed()).thenReturn(false);

        boolean result = immudbService.verifyConnection(5);

        assertTrue(result);
    }

    @Test
    void testPut() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        BatchEventEntity event = new BatchEventEntity();
        immudbService.put(event, BatchEventEntity.class);

        verify(mockStatement, times(1)).execute(startsWith("INSERT INTO"));
    }

    @Test
    void testGetHistory() throws Exception {
        Long entityId = 1L;
        String type = "BATCH";
        List<HistoryResponseDto> expectedHistory = new ArrayList<>();
        HistoryResponseDto dto1 = new HistoryResponseDto();
        dto1.setEntityId("entity1");
        expectedHistory.add(dto1);
        mockedStatic = mockStatic(HttpUtils.class);
        when(HttpUtils.callGetAPI(any(),any())).thenReturn("123");

        doNothing().when(mockClient).openSession(anyString(), anyString(), anyString());
        doNothing().when(mockClient).beginTransaction();
        SQLQueryResult mockResult = mock(SQLQueryResult.class);


        when(mockClient.sqlQuery(anyString())).thenReturn(mockResult);
        when(mockResult.next()).thenReturn(true, false,true, false);
        when(mockResult.getColumnsCount()).thenReturn(10);

        when(mockResult.getColumnName(0)).thenReturn("id");
        when(mockResult.getInt(0)).thenReturn(1);
        when(mockResult.getColumnType(0)).thenReturn("INTEGER");

        when(mockResult.getColumnName(1)).thenReturn("entityid");
        when(mockResult.getString(1)).thenReturn("entity1");
        when(mockResult.getColumnType(1)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(2)).thenReturn("manufacturername");
        when(mockResult.getString(2)).thenReturn("Acme Inc.");
        when(mockResult.getColumnType(2)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(3)).thenReturn("manufactureraddress");
        when(mockResult.getString(3)).thenReturn("123 Main St.");
        when(mockResult.getColumnType(3)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(4)).thenReturn("type");
        when(mockResult.getString(4)).thenReturn("BATCH");
        when(mockResult.getColumnType(4)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(5)).thenReturn("state");
        when(mockResult.getString(5)).thenReturn("ACTIVE");
        when(mockResult.getColumnType(5)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(6)).thenReturn("comments");
        when(mockResult.getString(6)).thenReturn("This is a test batch.");
        when(mockResult.getColumnType(6)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(7)).thenReturn("dateofaction");
        when(mockResult.getDate(7)).thenReturn(new Date());
        when(mockResult.getColumnType(7)).thenReturn("TIMESTAMP");

        when(mockResult.getColumnName(8)).thenReturn("createdby");
        when(mockResult.getString(8)).thenReturn("user1");
        when(mockResult.getColumnType(8)).thenReturn("VARCHAR");

        when(mockResult.getColumnName(9)).thenReturn("createddate");
        when(mockResult.getDate(9)).thenReturn(new Date());
        when(mockResult.getColumnType(9)).thenReturn("TIMESTAMP");

        when(mockResult.getColumnName(10)).thenReturn("");
        when(mockResult.getString(10)).thenReturn("");

        when(mockHistoryMapper.mapHistoryEntityToDto(any(BatchEventEntity.class))).thenReturn(dto1);

        List<HistoryResponseDto> actualHistory = immudbService.getHistory(entityId, type);

        assertNotNull(actualHistory);
        verify(mockClient, times(1)).beginTransaction();
        verify(mockClient, times(1)).sqlQuery(anyString());
        verify(mockClient, times(1)).closeSession();

        //morecase when result contains key 10

        when(mockResult.getString(10)).thenReturn("notempty");
        when(mockResult.getString(5)).thenReturn("Sample sent to Lab");

        actualHistory = immudbService.getHistory(entityId, type);
        assertNotNull(actualHistory);

        mockedStatic.close();
    }

    @Test
    void testGetHistoryForEntities() {
        List<Long> entityIds = List.of(1L, 2L);
        String type = "BATCH";
        Map<String, List<HistoryResponseDto>> expectedResult = new HashMap<>();
        List<HistoryResponseDto> history1 = List.of(new HistoryResponseDto());
        expectedResult.put("1", history1);

        doReturn(history1).when(immudbService).getHistory(1L, type);


        Map<String, List<HistoryResponseDto>> actualResult = immudbService.getHistoryForEntities(entityIds, type);

        assertNotNull(actualResult);
        verify(immudbService, times(1)).getHistory(1L, type);


    }

    @Test
    void testGetHistoryWithHistoryRequestDto() {
        HistoryRequestDto dto = new HistoryRequestDto();
        dto.setBatchId(1L);
        dto.setLotId(2L);

        List<HistoryResponseDto> batchHistory = List.of(new HistoryResponseDto());
        List<HistoryResponseDto> lotHistory = List.of(new HistoryResponseDto());
        List<HistoryResponseDto> expectedHistory = new ArrayList<>(batchHistory);
        expectedHistory.addAll(lotHistory);
        Map<String, List<HistoryResponseDto>> expectedResult = new HashMap<>();
        expectedResult.put("history", expectedHistory);

        doReturn(batchHistory).when(immudbService).getHistory(1L, "BATCH");
        doReturn(lotHistory).when(immudbService).getHistory(2L, "LOT");

        Map<String, List<HistoryResponseDto>> actualResult = immudbService.getHistory(dto);

        assertEquals(expectedResult, actualResult);
        verify(immudbService, times(1)).getHistory(1L, "BATCH");
        verify(immudbService, times(1)).getHistory(2L, "LOT");

        dto.setLotId(null);
        actualResult = immudbService.getHistory(dto);
        assertNotNull(actualResult);

        dto.setBatchId(null);
        actualResult = immudbService.getHistory(dto);
        assertNotNull(actualResult);

        dto.setLotId(2L);
        actualResult = immudbService.getHistory(dto);
        assertNotNull(actualResult);



    }
    @Test
    void testConvertToEntity() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("entityid", "entity1");
        map.put("manufacturername", "Acme Inc.");
        map.put("manufactureraddress", "123 Main St.");
        map.put("type", "BATCH");
        map.put("state", "ACTIVE");
        map.put("comments", "This is a test batch.");
        map.put("dateofaction", 1640995200000L);
        map.put("createdby", "user1");
        map.put("createddate", 1640995200000L);

        BatchEventEntity entity = immudbService.convertToEntity(map);

        assertEquals(1L, entity.getId());
        assertEquals("entity1", entity.getEntityId());
        assertEquals("Acme Inc.", entity.getManufacturerName());
        assertEquals("123 Main St.", entity.getManufacturerAddress());
        assertEquals("BATCH", entity.getType());
        assertEquals("ACTIVE", entity.getState());
        assertEquals("This is a test batch.", entity.getComments());
        assertEquals(new java.sql.Timestamp(1640995200000L), entity.getDateOfAction());
        assertEquals("user1", entity.getCreatedBy());
        assertEquals(new java.sql.Timestamp(1640995200000L), entity.getCreatedDate());
    }
}