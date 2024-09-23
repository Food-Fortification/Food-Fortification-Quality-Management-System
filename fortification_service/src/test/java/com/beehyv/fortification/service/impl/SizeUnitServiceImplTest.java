package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.SizeUnitResponseDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.SizeUnit;
import com.beehyv.fortification.entity.UOM;
import com.beehyv.fortification.manager.BatchManager;
import com.beehyv.fortification.manager.SizeUnitManager;
import com.beehyv.fortification.manager.UOMManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SizeUnitServiceImplTest {

    @Mock
    private SizeUnitManager manager;

    @Mock
    private BatchManager batchManager;

    @Mock
    private UOMManager uomManager;

    @Mock
    private BatchServiceImpl batchService;

    @InjectMocks
    private SizeUnitServiceImpl sizeUnitService;

    private SizeUnitRequestDto requestDto;
    private SizeUnit sizeUnit;
    private Batch batch;
    private UOM uom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new SizeUnitRequestDto();
        requestDto.setUomId(1L);
        requestDto.setSize(10L);
        requestDto.setQuantity(5.0);

        sizeUnit = new SizeUnit();
        sizeUnit.setId(1L);
        sizeUnit.setUom(new UOM(1L));
        sizeUnit.setSize(10L);
        sizeUnit.setQuantity(5.0);

        batch = new Batch();
        batch.setId(1L);
        batch.setRemainingQuantity(100.0);

        uom = new UOM();
        uom.setId(1L);
        uom.setConversionFactorKg(1L);

        when(manager.create(any(SizeUnit.class))).thenReturn(sizeUnit);
        when(manager.findById(sizeUnit.getId())).thenReturn(sizeUnit);
        when(batchManager.findById(batch.getId())).thenReturn(batch);
        when(uomManager.findAllByIds(eq(List.of(uom.getId())))).thenReturn(List.of(uom));
        when(batchService.checkLabAccess(batch.getId())).thenReturn(true);
    }

    @Test
    void createSizeUnit_ValidRequest_ShouldCreateSizeUnit() {
        sizeUnitService.createSizeUnit(requestDto);

        verify(manager, times(1)).create(any(SizeUnit.class));
    }

    @Test
    void getSizeUnitById_ValidId_ShouldReturnSizeUnitDto() {
        SizeUnitResponseDto response = sizeUnitService.getSizeUnitById(sizeUnit.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(sizeUnit.getId(), response.getId());
        Assertions.assertEquals(sizeUnit.getUom().getId(), response.getUom().getId());
        Assertions.assertEquals(sizeUnit.getSize(), response.getSize());
        Assertions.assertEquals(sizeUnit.getQuantity(), response.getQuantity());
    }

    @Test
    void getAllSizeUnits_ValidRequest_ShouldReturnSizeUnitList() {
        List<SizeUnit> sizeUnits = new ArrayList<>();
        sizeUnits.add(sizeUnit);

        when(manager.findAllByBatchId(batch.getId(), 0, 10)).thenReturn(sizeUnits);
        when(manager.getCount(sizeUnits.size(), batch.getId(), 0, 10)).thenReturn(1L);

        ListResponse<SizeUnitResponseDto> response = sizeUnitService.getAllSizeUnits(batch.getId(), 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getCount());
        Assertions.assertEquals(1, response.getCount());
    }

    @Test
    void updateSizeUnit_ValidRequest_ShouldUpdateSizeUnit() {
        requestDto.setId(sizeUnit.getId());
        requestDto.setSize(20L);
        requestDto.setQuantity(10.0);

        when(manager.update(any(SizeUnit.class))).thenReturn(sizeUnit);

        sizeUnitService.updateSizeUnit(requestDto);

        Assertions.assertEquals(20L, sizeUnit.getSize());
        Assertions.assertEquals(10.0, sizeUnit.getQuantity());
        verify(manager, times(1)).update(sizeUnit);
    }

    @Test
    void deleteSizeUnit_ValidId_ShouldDeleteSizeUnit() {
        sizeUnitService.deleteSizeUnit(sizeUnit.getId());

        verify(manager, times(1)).delete(sizeUnit.getId());
    }

    @Test
    void createSizeUnits_ValidRequest_ShouldCreateSizeUnits() {
        List<SizeUnitRequestDto> sizeUnits = new ArrayList<>();
        sizeUnits.add(requestDto);

        boolean result = sizeUnitService.createSizeUnits(sizeUnits, batch.getId());

        Assertions.assertTrue(result);
        verify(manager, times(1)).create(any(SizeUnit.class));
    }

    @Test
    void updateSizeUnits_ValidRequest_ShouldUpdateSizeUnits() {
        List<SizeUnitRequestDto> sizeUnits = new ArrayList<>();
        sizeUnits.add(requestDto);

        SizeUnit existingSizeUnit = new SizeUnit();
        existingSizeUnit.setId(2L);
        existingSizeUnit.setUom(new UOM(1L));
        existingSizeUnit.setSize(20L);
        existingSizeUnit.setQuantity(10.0);

        List<SizeUnit> existingSizeUnits = new ArrayList<>();
        existingSizeUnits.add(existingSizeUnit);

        when(manager.findAllByBatchId(batch.getId(), null, null)).thenReturn(existingSizeUnits);
        when(manager.update(any(SizeUnit.class))).thenReturn(sizeUnit);

        boolean result = sizeUnitService.updateSizeUnits(sizeUnits, batch.getId());

        Assertions.assertTrue(result);
        verify(manager, times(1)).update(any(SizeUnit.class));
        verify(manager, times(1)).delete(existingSizeUnit.getId());
    }
}


//  test includes=
//createSizeUnit_ValidRequest_ShouldCreateSizeUnit: This test verifies that the createSizeUnit method creates a new SizeUnit entity correctly.
//getSizeUnitById_ValidId_ShouldReturnSizeUnitDto: This test verifies that the getSizeUnitById method returns the correct SizeUnitResponseDto for a given SizeUnit ID.
//getAllSizeUnits_ValidRequest_ShouldReturnSizeUnitList: This test verifies that the getAllSizeUnits method returns a ListResponse containing the correct list of SizeUnitResponseDto objects.
//updateSizeUnit_ValidRequest_ShouldUpdateSizeUnit: This test verifies that the updateSizeUnit method updates an existing SizeUnit entity correctly with the provided SizeUnitRequestDto.
//deleteSizeUnit_ValidId_ShouldDeleteSizeUnit: This test verifies that the deleteSizeUnit method deletes an existing SizeUnit