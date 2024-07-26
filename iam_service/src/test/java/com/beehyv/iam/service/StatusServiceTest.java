package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.StatusRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.StatusResponseDto;
import com.beehyv.iam.manager.StatusManager;
import com.beehyv.iam.model.Status;
import com.beehyv.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatusServiceTest {

    @Mock
    private StatusManager statusManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private StatusService statusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Status status = new Status();
        when(statusManager.findById(id)).thenReturn(status);
        StatusResponseDto responseDto = new StatusResponseDto();
        when(dtoMapper.mapToResponseDto(status)).thenReturn(responseDto);

        StatusResponseDto result = statusService.getById(id);

        assertEquals(responseDto, result);
        verify(statusManager, times(1)).findById(id);
        verify(dtoMapper, times(1)).mapToResponseDto(status);
    }

    @Test
    public void testCreate() {
        StatusRequestDto requestDto = new StatusRequestDto();
        Status status = new Status();
        status.setId(1L);
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(status);
        when(statusManager.create(status)).thenReturn(status);

        Long result = statusService.create(requestDto);

        assertEquals(1L, result);
        verify(dtoMapper, times(1)).mapToEntity(requestDto);
        verify(statusManager, times(1)).create(status);
    }

    @Test
    public void testUpdate() {
        StatusRequestDto requestDto = new StatusRequestDto();
        Status status = new Status();
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(status);

        statusService.update(requestDto);

        verify(dtoMapper, times(1)).mapToEntity(requestDto);
        verify(statusManager, times(1)).update(status);
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        statusService.delete(id);

        verify(statusManager, times(1)).delete(id);
    }

    @Test
    public void testFindAll() {
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Status> entities = new ArrayList<>();
        entities.add(new Status());
        when(statusManager.findAll(pageNumber, pageSize)).thenReturn(entities);
        Long count = 1L;
        when(statusManager.getCount(entities.size(), pageNumber, pageSize)).thenReturn(count);
        List<StatusResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(new StatusResponseDto());
        when(dtoMapper.mapToResponseDto(entities.get(0))).thenReturn(responseDtos.get(0));

        ListResponse<StatusResponseDto> result = statusService.findAll(pageNumber, pageSize);

        assertEquals(entities.size(), result.getData().size());
        verify(statusManager, times(1)).findAll(pageNumber, pageSize);
        verify(statusManager, times(1)).getCount(entities.size(), pageNumber, pageSize);
        verify(dtoMapper, times(entities.size())).mapToResponseDto(any(Status.class));
    }
}
