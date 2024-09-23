package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.StatusRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.StatusResponseDto;
import com.beehyv.lab.entity.Status;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.manager.StatusManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class StatusServiceImplTest {

    @Mock
    private StatusManager statusManager;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private StatusServiceImpl statusService;

    private Status status;
    private StatusRequestDTO statusRequestDTO;
    private StatusResponseDto statusResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        status = new Status();
        status.setId(1L);
        statusRequestDTO = new StatusRequestDTO();
        statusResponseDto = new StatusResponseDto();
    }

    @Test
    void testGetById() {
        when(statusManager.findById(1L)).thenReturn(status);
        when(dtoMapper.mapToResponseDto(status)).thenReturn(statusResponseDto);

        StatusResponseDto result = statusService.getById(1L);

        assertEquals(statusResponseDto, result);
    }

    @Test
    void testCreate() {
        when(dtoMapper.mapToEntity(statusRequestDTO)).thenReturn(status);
        when(statusManager.create(status)).thenReturn(status);

        Long id = statusService.create(statusRequestDTO);

        assertEquals(status.getId(), id);
    }

    @Test
    void testUpdate() {
        when(dtoMapper.mapToEntity(statusRequestDTO)).thenReturn(status);

        statusService.update(statusRequestDTO);

        verify(statusManager, times(1)).update(status);
    }

    @Test
    void testDelete() {
        statusService.delete(1L);

        verify(statusManager, times(1)).delete(1L);
    }

    @Test
    void testFindAll() {
        List<Status> statuses = Arrays.asList(status);
        when(statusManager.findAll(0, 10)).thenReturn(statuses);
        when(dtoMapper.mapToResponseDto(status)).thenReturn(statusResponseDto);
        when(statusManager.getCount(1, 0, 10)).thenReturn(1L);

        ListResponse<StatusResponseDto> result = statusService.findAll(0, 10);

        assertEquals(1, result.getData().size());
        assertEquals(statusResponseDto, result.getData().get(0));
        assertEquals(1L, result.getCount());
    }
}



//
//Explanations:
//
//testGetById: This test case verifies the getById method. It mocks the statusManager.findById and dtoMapper.mapToResponseDto methods, and asserts that the correct StatusResponseDto is returned.
//        testCreate: This test case verifies the create method. It mocks the dtoMapper.mapToEntity and statusManager.create methods, and asserts that the correct ID is returned.
//        testUpdate: This test case verifies the update method. It mocks the dtoMapper.mapToEntity method, and verifies that the statusManager.update method is called exactly once with the correct arguments.
//testDelete: This test case verifies the delete method. It verifies that the statusManager.delete method is called exactly once with the correct arguments.
//testFindAll: This test case verifies the findAll method. It mocks the statusManager.findAll, dtoMapper.mapToResponseDto, and statusManager.getCount methods, and asserts that the correct ListResponse is returned with the expected data and count.