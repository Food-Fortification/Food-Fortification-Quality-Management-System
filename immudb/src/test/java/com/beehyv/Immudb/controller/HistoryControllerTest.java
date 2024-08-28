package com.beehyv.Immudb.controller;

import com.beehyv.Immudb.dto.HistoryRequestDto;
import com.beehyv.Immudb.service.ImmudbService;
import com.beehyv.parent.exceptions.CustomException;
import io.codenotary.immudb4j.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HistoryControllerTest {

    @InjectMocks
    HistoryController historyController;

    @Mock
    ImmudbService immudbService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHistoryNoException() throws SQLException, java.sql.SQLException {
        List<Long> entityIds = Arrays.asList(1L, 2L, 3L);
        String type = "testType";
        when(immudbService.getHistoryForEntities(entityIds, type)).thenReturn(Collections.emptyMap());

        ResponseEntity<?> response = historyController.getHistory(entityIds, type);

        assertEquals(200, response.getStatusCodeValue());
        verify(immudbService, times(1)).getHistoryForEntities(entityIds, type);
    }

    @Test
    public void testGetHistoryWithCustomException() throws SQLException, java.sql.SQLException {
        List<Long> entityIds = Arrays.asList(1L, 2L, 3L);
        String type = "testType";
        when(immudbService.getHistoryForEntities(entityIds, type)).thenThrow(CustomException.class);

        ResponseEntity<?> response = historyController.getHistory(entityIds, type);

        assertEquals(204, response.getStatusCodeValue());
        verify(immudbService, times(1)).getHistoryForEntities(entityIds, type);
    }

    @Test
    public void testGetHistoryWithSQLException() throws SQLException, java.sql.SQLException {
        List<Long> entityIds = Arrays.asList(1L, 2L, 3L);
        String type = "testType";
        when(immudbService.getHistoryForEntities(entityIds, type)).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = historyController.getHistory(entityIds, type);

        assertEquals(500, response.getStatusCodeValue());
        verify(immudbService, times(1)).getHistoryForEntities(entityIds, type);
    }

    @Test
    public void testPostGetHistoryNoException() {
        HistoryRequestDto dto = new HistoryRequestDto(); // populate this as needed
        when(immudbService.getHistory(dto)).thenReturn(Collections.emptyMap());

        ResponseEntity<?> response = historyController.getHistory(dto);

        assertEquals(200, response.getStatusCodeValue());
        verify(immudbService, times(1)).getHistory(dto);
    }

    @Test
    public void testPostGetHistoryWithException() {
        HistoryRequestDto dto = new HistoryRequestDto(); // populate this as needed
        when(immudbService.getHistory(dto)).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = historyController.getHistory(dto);

        assertEquals(500, response.getStatusCodeValue());
        verify(immudbService, times(1)).getHistory(dto);
    }
}
