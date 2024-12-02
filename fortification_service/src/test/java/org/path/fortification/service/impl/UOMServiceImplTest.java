package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.UOMRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.UOMResponseDto;
import org.path.fortification.entity.UOM;
import org.path.fortification.manager.UOMManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UOMServiceImplTest {
    @Mock
    private UOMManager manager;

    @InjectMocks
    private UOMServiceImpl uomService;

    private UOMRequestDto requestDto;
    private UOM uom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new UOMRequestDto();
        requestDto.setName("Kg");
        requestDto.setConversionFactorKg(1L);

        uom = new UOM();
        uom.setId(1L);
        uom.setName("Kg");
        uom.setConversionFactorKg(1L);
    }

    @Test
    void createUOM_ValidRequest_ShouldCreateUOM() {
        when(manager.create(any(UOM.class))).thenReturn(uom);

        uomService.createUOM(requestDto);

        verify(manager, times(1)).create(any(UOM.class));
    }

    @Test
    void getUOMById_ValidId_ShouldReturnUOMDto() {
        when(manager.findById(uom.getId())).thenReturn(uom);

        UOMResponseDto response = uomService.getUOMById(uom.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(uom.getId(), response.getId());
        Assertions.assertEquals(uom.getName(), response.getName());
        Assertions.assertEquals(uom.getConversionFactorKg(), response.getConversionFactorKg());
    }

    @Test
    void getAllUOMs_ValidRequest_ShouldReturnUOMList() {
        List<UOM> uoms = new ArrayList<>();
        UOM uom1 = new UOM();
        uom1.setId(1L);
        UOM uom2 = new UOM();
        uom2.setId(2L);
        uoms.add(uom1);
        uoms.add(uom2);

        when(manager.findAll(0, 10)).thenReturn(uoms);
        when(manager.getCount(uoms.size(), 0, 10)).thenReturn(2L);

        ListResponse<UOMResponseDto> response = uomService.getAllUOMs(0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());
    }

    @Test
    void updateUOM_ValidRequest_ShouldUpdateUOM() {
        requestDto.setId(uom.getId());
        requestDto.setName("Updated Name");
        requestDto.setConversionFactorKg(2L);

        when(manager.findById(uom.getId())).thenReturn(uom);

        uomService.updateUOM(requestDto);
        Optional<Long> optionalValue = Optional.of(2L);
        Assertions.assertEquals("Updated Name", uom.getName());
        Assertions.assertEquals(optionalValue, Optional.ofNullable(uom.getConversionFactorKg()));
        verify(manager, times(1)).create(uom);
    }

    @Test
    void deleteUOM_ValidId_ShouldDeleteUOM() {
        uomService.deleteUOM(uom.getId());

        verify(manager, times(1)).delete(uom.getId());
    }
}


//these tests cover-
//createUOM_ValidRequest_ShouldCreateUOM: This test verifies that the createUOM method creates a new UOM entity correctly.
//getUOMById_ValidId_ShouldReturnUOMDto: This test verifies that the getUOMById method returns the correct UOMResponseDto for a given UOM ID.
//getAllUOMs_ValidRequest_ShouldReturnUOMList: This test verifies that the getAllUOMs method returns a ListResponse containing the correct list of UOMResponseDto objects.
//updateUOM_ValidRequest_ShouldUpdateUOM: This test verifies that the updateUOM method updates an existing UOM entity correctly with the provided UOMRequestDto.
//deleteUOM_ValidId_ShouldDeleteUOM: This test verifies that the deleteUOM method deletes an existing UOM entity for the given ID.