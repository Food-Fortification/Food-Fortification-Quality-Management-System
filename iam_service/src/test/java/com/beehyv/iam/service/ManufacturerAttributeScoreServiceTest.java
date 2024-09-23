package com.beehyv.iam.service;

import com.beehyv.iam.dao.ManufacturerAttributeScoreDao;
import com.beehyv.iam.dto.requestDto.ManufacturerAttributeScoreRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerAttributeScoreResponseDto;
import com.beehyv.iam.manager.BaseManager;
import com.beehyv.iam.manager.ManufacturerAttributeScoreManager;
import com.beehyv.iam.model.ManufacturerAttributeScore;
import com.beehyv.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ManufacturerAttributeScoreServiceTest {

    @Mock
    private ManufacturerAttributeScoreManager manufacturerAttributeScoreManager;
    @Mock
    private BaseManager<ManufacturerAttributeScore, ManufacturerAttributeScoreDao> baseManager;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private ManufacturerAttributeScoreService manufacturerAttributeScoreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        ManufacturerAttributeScore entity = new ManufacturerAttributeScore();
        ManufacturerAttributeScoreResponseDto dto = new ManufacturerAttributeScoreResponseDto();

        entity.setId(id);
        entity.setUuid("sample-uuid");
        entity.setValue("sample-value");

        when(manufacturerAttributeScoreManager.findById(id)).thenReturn(entity);
        when(dtoMapper.mapToDto(entity)).thenReturn(dto);

        ManufacturerAttributeScoreResponseDto result = manufacturerAttributeScoreService.getById(id);


    }


    @Test
    public void testCreate() {
        ManufacturerAttributeScoreRequestDto requestDto = new ManufacturerAttributeScoreRequestDto();
        ManufacturerAttributeScore entity = new ManufacturerAttributeScore();
        entity.setId(1L);

        ManufacturerAttributeScore createdEntity = new ManufacturerAttributeScore();
        createdEntity.setId(1L);

        when(dtoMapper.mapToEntity(requestDto)).thenReturn(entity);
        when(manufacturerAttributeScoreManager.create(any())).thenReturn(createdEntity);
        Long result = manufacturerAttributeScoreService.create(requestDto);

        assertEquals(createdEntity.getId(), result);
    }

    @Test
    public void testUpdate() {
        ManufacturerAttributeScoreRequestDto requestDto = new ManufacturerAttributeScoreRequestDto();
        ManufacturerAttributeScore entity = new ManufacturerAttributeScore();

        when(dtoMapper.mapToEntity(requestDto)).thenReturn(entity);
        when(manufacturerAttributeScoreManager.update(any())).thenReturn(entity);
        manufacturerAttributeScoreService.update(requestDto);


    }


    @Test
    public void testDelete() {
        Long id = 1L;

        manufacturerAttributeScoreService.delete(id);

        verify(manufacturerAttributeScoreManager, times(1)).delete(id);
    }
}
