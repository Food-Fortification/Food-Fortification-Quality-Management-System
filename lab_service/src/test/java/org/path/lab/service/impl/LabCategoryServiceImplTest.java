package org.path.lab.service.impl;
import org.path.lab.dto.requestDto.LabCategoryRequestDto;
import org.path.lab.dto.responseDto.LabCategoryResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.LabCategory;
import org.path.lab.manager.LabCategoryManager;
import org.path.lab.mapper.DTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LabCategoryServiceImplTest {

    @Mock
    private LabCategoryManager manager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private LabCategoryServiceImpl labCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        LabCategoryRequestDto labCategoryRequestDto = new LabCategoryRequestDto(1L, 1L, true, 1L);
        when(mapper.mapDtoToEntityLabCategory(any())).thenReturn(new LabCategory());

        assertDoesNotThrow(() -> labCategoryService.create(labCategoryRequestDto));

        verify(manager, times(1)).create(any(LabCategory.class));
    }

    @Test
    void testGetById() {
        when(manager.findById(1L)).thenReturn(new LabCategory());
        when(mapper.mapEntityToDtoLabCategory(any())).thenReturn(new LabCategoryResponseDto());

        LabCategoryResponseDto result = labCategoryService.getById(1L);

        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        LabCategoryRequestDto labCategoryRequestDto = new LabCategoryRequestDto(1L, 1L, true, 1L);
        when(mapper.mapDtoToEntityLabCategory(any())).thenReturn(new LabCategory());

        assertDoesNotThrow(() -> labCategoryService.update(labCategoryRequestDto));

        verify(manager, times(1)).update(any(LabCategory.class));
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> labCategoryService.delete(1L));

        verify(manager, times(1)).delete(1L);
    }

    @Test
    void testGetByLabId() {
        List<LabCategory> labCategories = Collections.singletonList(new LabCategory());
        when(manager.findAllByLabId(1L)).thenReturn(labCategories);
        when(mapper.mapEntityToDtoLabCategory(any())).thenReturn(new LabCategoryResponseDto());

        ListResponse<LabCategoryResponseDto> result = labCategoryService.getByLabId(1L);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
    }

    @Test
    void testGetCategoryIdsByLabId() {
        List<LabCategory> labCategories = Collections.singletonList(new LabCategory());
        when(manager.findAllByLabId(1L)).thenReturn(labCategories);

        List<Long> result = labCategoryService.getCategoryIdsByLabId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
