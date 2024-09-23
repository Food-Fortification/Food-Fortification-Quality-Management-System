package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.AttributeCategoryRequestDto;
import com.beehyv.iam.dto.requestDto.AttributeRequestDto;
import com.beehyv.iam.dto.responseDto.AttributeCategoryResponseDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.manager.AttributeCategoryManager;
import com.beehyv.iam.model.AttributeCategory;
import com.beehyv.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;

class AttributeCategoryServiceTest {

    @Mock
    private AttributeCategoryManager attributeCategoryManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private AttributeCategoryService attributeCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        // Given
        AttributeCategory entity = new AttributeCategory();
        AttributeCategoryResponseDto responseDto = new AttributeCategoryResponseDto();
        when(attributeCategoryManager.findById(anyLong())).thenReturn(entity);
        when(dtoMapper.mapToDto(entity)).thenReturn(responseDto);

        AttributeCategoryResponseDto result = attributeCategoryService.getById(1L);

        assertNotNull(result);
    }

    @Test
    void testFindAll() {
        AttributeCategory entity1 = new AttributeCategory();
        AttributeCategory entity2 = new AttributeCategory();
        List<AttributeCategory> entities = Arrays.asList(entity1, entity2);
        AttributeCategoryResponseDto responseDto1 = new AttributeCategoryResponseDto();
        AttributeCategoryResponseDto responseDto2 = new AttributeCategoryResponseDto();
        when(attributeCategoryManager.findAll(anyInt(), anyInt())).thenReturn(entities);
        when(attributeCategoryManager.getCount(anyInt(), anyInt(), anyInt())).thenReturn(2L);
        when(dtoMapper.mapToDto(entity1)).thenReturn(responseDto1);
        when(dtoMapper.mapToDto(entity2)).thenReturn(responseDto2);

        ListResponse<AttributeCategoryResponseDto> result = attributeCategoryService.findAll(1, 10);

        assertNotNull(result);
        assertEquals(2, result.getData().size());
        assertEquals(2L, result.getCount());
    }


    @Test
    void testCreate() {
        // Given
        AttributeCategoryRequestDto requestDto = new AttributeCategoryRequestDto();
        requestDto.setId(1L);
        requestDto.setCategory("Test Category");
        Set<AttributeRequestDto> attributes = new HashSet<>();
        AttributeRequestDto attributeRequestDto = new AttributeRequestDto();
        attributes.add(attributeRequestDto);
        requestDto.setAttributes(attributes);

        AttributeCategory entity = new AttributeCategory();
        entity.setId(1L);
        when(dtoMapper.mapToEntity(any(AttributeCategoryRequestDto.class))).thenReturn(entity);
        when(attributeCategoryManager.create(any(AttributeCategory.class))).thenReturn(entity);

        Long id = attributeCategoryService.create(requestDto);

        assertNotNull(id);
        assertEquals(1L, id);
    }

    @Test
    void testUpdate() {
        AttributeCategoryRequestDto requestDto = new AttributeCategoryRequestDto();
        requestDto.setId(1L);
        requestDto.setCategory("Test Category");
        Set<AttributeRequestDto> attributes = new HashSet<>();
        AttributeRequestDto attributeRequestDto = new AttributeRequestDto();
        attributes.add(attributeRequestDto);
        requestDto.setAttributes(attributes);

        AttributeCategory entity = new AttributeCategory();
        entity.setId(1L);
        when(dtoMapper.mapToEntity(any(AttributeCategoryRequestDto.class))).thenReturn(entity);

        attributeCategoryService.update(requestDto);

        ArgumentCaptor<AttributeCategory> captor = ArgumentCaptor.forClass(AttributeCategory.class);
        verify(attributeCategoryManager, times(1)).update(captor.capture());
        AttributeCategory capturedEntity = captor.getValue();

        assertEquals(entity.getId(), capturedEntity.getId());
    }

    @Test
    void testDelete() {
        Long id = 1L;

        attributeCategoryService.delete(id);

        verify(attributeCategoryManager, times(1)).delete(eq(id));
    }
}
