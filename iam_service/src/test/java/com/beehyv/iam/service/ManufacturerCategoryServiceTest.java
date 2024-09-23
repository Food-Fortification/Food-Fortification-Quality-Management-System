package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.ManufacturerCategoryRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerCategoryResponseDto;
import com.beehyv.iam.manager.ManufacturerCategoryManager;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.model.ManufacturerCategory;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManufacturerCategoryServiceTest {

    @Mock
    private ManufacturerCategoryManager manufacturerCategoryManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private BaseMapper<ManufacturerCategoryResponseDto, ManufacturerCategoryRequestDto, ManufacturerCategory> manufacturerCategoryMapper;

    @InjectMocks
    private ManufacturerCategoryService manufacturerCategoryService;

    private ManufacturerCategoryRequestDto mockRequestDto;
    private ManufacturerCategory mockEntity;
    private ManufacturerCategoryResponseDto mockResponseDto;

    @BeforeEach
    void setUp() {
        mockRequestDto = new ManufacturerCategoryRequestDto();
        mockEntity = new ManufacturerCategory();
        mockEntity.setId(1L);
        mockResponseDto = new ManufacturerCategoryResponseDto();
    }


    @Test
    void testCreate() {

        manufacturerCategoryService.create(mockRequestDto);

        ArgumentCaptor<ManufacturerCategory> argumentCaptor = ArgumentCaptor.forClass(ManufacturerCategory.class);
        verify(manufacturerCategoryManager).create(argumentCaptor.capture());

        ManufacturerCategory capturedArgument = argumentCaptor.getValue();
        assertNotNull(capturedArgument);
    }

    @Test
    void testUpdate() {
        // Setup
        ManufacturerCategoryRequestDto mockRequestDto = new ManufacturerCategoryRequestDto();
        ManufacturerCategory mockEntity = new ManufacturerCategory();

        // Mock the behavior of the mapper

        // Invoke the method under test
        manufacturerCategoryService.update(mockRequestDto);

        // Verify that the update method of the manager is called with the correct argument
        ArgumentCaptor<ManufacturerCategory> argumentCaptor = ArgumentCaptor.forClass(ManufacturerCategory.class);
        verify(manufacturerCategoryManager).update(argumentCaptor.capture());

        // Extract the captured argument and perform assertions
        ManufacturerCategory capturedArgument = argumentCaptor.getValue();
        assertNotNull(capturedArgument);
        // Perform any necessary assertions on the captured argument
        // For example:
    }


    @Test
    void testDeleteById() {
        manufacturerCategoryService.deleteById(1L);
        verify(manufacturerCategoryManager).delete(1L);
    }

    @Test
    void testGetCategoriesForManufacturer() {
        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", "1"));
        List<ManufacturerCategory> mockEntities = List.of(mockEntity);
        when(manufacturerCategoryManager.findAllByManufacturerId(1L)).thenReturn(mockEntities);

        List<Long> result = manufacturerCategoryService.getCategoriesForManufacturer(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockEntity.getCategoryId(), result.get(0));
        verify(keycloakInfo).getUserInfo();
        verify(manufacturerCategoryManager).findAllByManufacturerId(1L);
    }

    @Test
    void testGetCanSkipRawMaterialsForManufacturerAndCategory() {
        when(manufacturerCategoryManager.getCanSkipRawMaterialsForManufacturerAndCategory(1L, 1L)).thenReturn(true);

        Boolean result = manufacturerCategoryService.getCanSkipRawMaterialsForManufacturerAndCategory(1L, 1L);

        assertTrue(result);
        verify(manufacturerCategoryManager).getCanSkipRawMaterialsForManufacturerAndCategory(1L, 1L);
    }

    @Test
    void testGetActionNameByManufacturerIdAndCategoryId() {
        when(manufacturerCategoryManager.getActionNameByManufacturerIdAndCategoryId(1L, 1L)).thenReturn("ActionName");

        String result = manufacturerCategoryService.getActionNameByManufacturerIdAndCategoryId(1L, 1L);

        assertEquals("ActionName", result);
        verify(manufacturerCategoryManager).getActionNameByManufacturerIdAndCategoryId(1L, 1L);
    }
}
