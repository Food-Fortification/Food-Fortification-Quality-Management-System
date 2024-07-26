package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.BatchPropertyRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchPropertyResponseDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.entity.BatchProperty;
import com.beehyv.fortification.manager.BatchPropertyManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BatchPropertyServiceImplTest {

    private BatchPropertyManager managerMock;
    private BatchPropertyServiceImpl batchPropertyService;

    @Before
    public void setUp() {
        managerMock = mock(BatchPropertyManager.class);
        batchPropertyService = new BatchPropertyServiceImpl(managerMock);
    }

    @Test
    public void testCreateBatchProperty() {
        BatchPropertyRequestDto requestDto = new BatchPropertyRequestDto();

        BatchProperty entity = new BatchProperty();
        when(managerMock.create(any(BatchProperty.class))).thenReturn(entity);

        batchPropertyService.createBatchProperty(requestDto);

        verify(managerMock, times(1)).create(any(BatchProperty.class));
    }

    @Test
    public void testGetBatchPropertyById() {
        Long propertyId = 1L;
        BatchProperty entity = new BatchProperty();
        when(managerMock.findById(propertyId)).thenReturn(entity);

        BatchPropertyResponseDto responseDto = batchPropertyService.getBatchPropertyById(propertyId);

        verify(managerMock, times(1)).findById(propertyId);
    }

    @Test
    public void testGetAllBatchProperties() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<BatchProperty> entities = new ArrayList<>();

        when(managerMock.findAll(pageNumber, pageSize)).thenReturn(entities);
        when(managerMock.getCount(anyInt(), eq(pageNumber), eq(pageSize))).thenReturn(10L);

        ListResponse<BatchPropertyResponseDto> response = batchPropertyService.getAllBatchProperties(pageNumber, pageSize);

        verify(managerMock, times(1)).findAll(pageNumber, pageSize);
    }

}
