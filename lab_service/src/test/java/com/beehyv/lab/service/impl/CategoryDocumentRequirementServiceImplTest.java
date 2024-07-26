package com.beehyv.lab.service.impl;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.beehyv.lab.dto.requestDto.CategoryDocumentRequirementRequestDTO;
import com.beehyv.lab.dto.responseDto.CategoryDocumentRequirementResponseDTO;
import com.beehyv.lab.dto.responseDto.DocTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.CategoryDocumentRequirement;
import com.beehyv.lab.enums.CategoryDocRequirementType;
import com.beehyv.lab.manager.CategoryDocumentRequirementManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.impl.CategoryDocumentRequirementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryDocumentRequirementServiceImplTest {

    @Mock
    private CategoryDocumentRequirementManager manager;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private CategoryDocumentRequirementServiceImpl service;

    private CategoryDocumentRequirement categoryDocumentRequirement;
    private CategoryDocumentRequirementRequestDTO requestDTO;
    private CategoryDocumentRequirementResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        categoryDocumentRequirement = new CategoryDocumentRequirement();
        categoryDocumentRequirement.setId(1L);

        requestDTO = new CategoryDocumentRequirementRequestDTO(
                1L,
                2L,
1L,                true,
                true,
                CategoryDocRequirementType.TEST
        );

        responseDTO = new CategoryDocumentRequirementResponseDTO(
                1L,
                2L,
                new DocTypeResponseDTO(1L, "TypeName"),
                true,
                true,
                CategoryDocRequirementType.TEST
        );
    }

    @Test
    void testGetAllCategoryDocumentRequirements() {
        List<CategoryDocumentRequirement> requirements = Collections.singletonList(categoryDocumentRequirement);
        when(manager.findAll(anyInt(), anyInt())).thenReturn(requirements);
        when(manager.getCount(anyInt(), anyInt(), anyInt())).thenReturn(1L);

        ListResponse<CategoryDocumentRequirementResponseDTO> result = service.getAllCategoryDocumentRequirements(1, 10);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
    }

    @Test
    void testGetAllCategoryDocumentRequirementsByCategoryIdAndType() {
        List<CategoryDocumentRequirement> requirements = Collections.singletonList(categoryDocumentRequirement);
        when(manager.findAllByCategoryIdAndType(anyLong(), any(), anyInt(), anyInt())).thenReturn(requirements);

        List<CategoryDocumentRequirementResponseDTO> result = service.getAllCategoryDocumentRequirementsByCategoryIdAndType(1L, CategoryDocRequirementType.TEST, 1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCreateCategoryDocumentRequirement() {

        service.createCategoryDocumentRequirement(requestDTO);

        verify(manager, times(1)).create(any(CategoryDocumentRequirement.class));
    }

    @Test
    void testGetCategoryDocumentRequirementById() {
        when(manager.findById(anyLong())).thenReturn(categoryDocumentRequirement);

        CategoryDocumentRequirementResponseDTO result = service.getCategoryDocumentRequirementById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateCategoryDocumentRequirement() {
        when(manager.findById(anyLong())).thenReturn(categoryDocumentRequirement);

        service.updateCategoryDocumentRequirement(requestDTO);

        verify(manager, times(1)).update(any(CategoryDocumentRequirement.class));
    }

    @Test
    void testDeleteCategoryDocumentRequirement() {
        when(manager.findById(anyLong())).thenReturn(categoryDocumentRequirement);

        service.deleteCategoryDocumentRequirement(1L);

        verify(manager, times(1)).delete(anyLong());
    }

    @Test
    void testGetAllCategoryDocumentRequirementsByType() {
        List<CategoryDocumentRequirement> requirements = Collections.singletonList(categoryDocumentRequirement);
        when(manager.findAll(any(CategoryDocRequirementType.class), anyInt(), anyInt())).thenReturn(requirements);
        when(manager.getCount(anyInt(), any(CategoryDocRequirementType.class), anyInt(), anyInt())).thenReturn(1L);

        ListResponse<CategoryDocumentRequirementResponseDTO> result = service.getAllCategoryDocumentRequirementsByType(CategoryDocRequirementType.TEST, 1, 10);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
    }
}
