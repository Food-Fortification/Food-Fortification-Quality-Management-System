package org.path.iam.service;

import org.path.iam.dao.AttributeCategoryScoreDao;
import org.path.iam.dto.requestDto.AttributeCategoryScoreRequestDto;
import org.path.iam.dto.responseDto.AttributeCategoryScoreResponseDto;
import org.path.iam.manager.AttributeCategoryScoreManager;
import org.path.iam.manager.ManufacturerCategoryAttributesManager;
import org.path.iam.model.AttributeCategoryScore;
import org.path.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttributeCategoryScoreServiceTest {

    private AttributeCategoryScoreService attributeCategoryScoreService;
    @Mock
    private AttributeCategoryScoreDao attributeCategoryScoreRepository;
    @Mock
    private AttributeCategoryScoreManager attributeCategoryScoreManager;
    @Mock
    private DtoMapper dtoMapper;
    @Mock
    private ManufacturerCategoryAttributesManager manufacturerCategoryAttributesManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        attributeCategoryScoreManager = mock(AttributeCategoryScoreManager.class);

        attributeCategoryScoreService = new AttributeCategoryScoreService(attributeCategoryScoreManager, manufacturerCategoryAttributesManager);
    }

    @Test
    void getById_ValidId_ReturnsDto() {
        Long id = 1L;
        AttributeCategoryScoreResponseDto expectedDto = new AttributeCategoryScoreResponseDto(); // Mock response DTO
        when(attributeCategoryScoreManager.findById(id)).thenReturn(new AttributeCategoryScore());
        when(attributeCategoryScoreManager.findById(any())).thenReturn(new AttributeCategoryScore());

        AttributeCategoryScoreResponseDto actualDto = attributeCategoryScoreService.getById(id);

        assertNotNull(actualDto);
    }


    @Test
    public void testCreate() {
        AttributeCategoryScoreRequestDto dto = new AttributeCategoryScoreRequestDto();
        dto.setManufacturerAttributeScores(new HashSet<>());

        AttributeCategoryScore entity = new AttributeCategoryScore();
        entity.setId(1L);

        when(dtoMapper.mapToEntity(any(AttributeCategoryScoreRequestDto.class))).thenReturn(entity);

        when(attributeCategoryScoreManager.create(any(AttributeCategoryScore.class))).thenReturn(entity);

        Long id = attributeCategoryScoreService.create(dto);

        assertEquals(entity.getId(), id);
    }


    @Test
    void update_ValidDto_NoExceptionsThrown() {
        AttributeCategoryScoreRequestDto dto = new AttributeCategoryScoreRequestDto();
        dto.setManufacturerAttributeScores(new HashSet<>());

        assertDoesNotThrow(() -> attributeCategoryScoreService.update(dto));
    }


    @Test
    void delete_ValidId_NoExceptionsThrown() {
        Long id = 1L;

        assertDoesNotThrow(() -> attributeCategoryScoreService.delete(id));
    }
}
