package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.BatchDocRequestDto;
import org.path.fortification.dto.responseDto.BatchDocResponseDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.entity.BatchDoc;
import org.path.fortification.manager.BatchDocManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BatchDocServiceImplTest {

    private BatchDocManager managerMock;
    private BatchDocServiceImpl batchDocService;

    @Before
    public void setUp() {
        managerMock = mock(BatchDocManager.class);
        batchDocService = new BatchDocServiceImpl(managerMock);
    }

    @Test
    public void testCreateBatchDoc() {
        BatchDocRequestDto requestDto = new BatchDocRequestDto();

        BatchDoc entity = new BatchDoc();
        when(managerMock.create(any(BatchDoc.class))).thenReturn(entity);

        batchDocService.createBatchDoc(requestDto);

        verify(managerMock, times(1)).create(any(BatchDoc.class));
    }

    @Test
    public void testGetBatchDocById() {
        Long docId = 1L;
        BatchDoc entity = new BatchDoc();
        when(managerMock.findById(docId)).thenReturn(entity);

        BatchDocResponseDto responseDto = batchDocService.getBatchDocById(docId);

        verify(managerMock, times(1)).findById(docId);
    }

    @Test
    public void testGetAllBatchDocsByBatchId() {
        Long batchId = 1L;
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<BatchDoc> entities = new ArrayList<>();

        when(managerMock.findAllByBatchId(batchId, pageNumber, pageSize)).thenReturn(entities);
        when(managerMock.getCount(anyInt(), eq(pageNumber), eq(pageSize))).thenReturn(10L);

        ListResponse<BatchDocResponseDto> response = batchDocService.getAllBatchDocsByBatchId(batchId, pageNumber, pageSize);

        verify(managerMock, times(1)).findAllByBatchId(batchId, pageNumber, pageSize);
    }

}
