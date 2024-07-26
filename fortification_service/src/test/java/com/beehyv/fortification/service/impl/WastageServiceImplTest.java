package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.external.ExternalLotDetailsResponseDto;
import com.beehyv.fortification.dto.requestDto.WastageRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.WastageResponseDto;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.manager.BatchManager;
import com.beehyv.fortification.manager.BatchWastageManager;
import com.beehyv.fortification.manager.LotManager;
import com.beehyv.fortification.manager.UOMManager;
import com.beehyv.parent.exceptions.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WastageServiceImplTest {


    @Mock
    private BatchWastageManager manager;

    @Mock
    private BatchManager batchManager;

    @Mock
    private LotManager lotManager;

    @Mock
    private UOMManager uomManager;

    @InjectMocks
    private WastageServiceImpl wastageService;

    private WastageRequestDto dto;
    private Batch batch;
    private Lot lot;
    private UOM uom;

    @BeforeEach
    void setUp() {


        dto = new WastageRequestDto();
        dto.setWastageQuantity(10.0);
        dto.setReportedDate(new Date());
        dto.setUomId(1L);

        uom = new UOM();
        uom.setId(1L);
        uom.setName("Kg");
        uom.setConversionFactorKg(1L);

        batch = new Batch();
        batch.setId(1L);
        batch.setDateOfManufacture(new Date(System.currentTimeMillis() - 1000000));
        batch.setRemainingQuantity(100.0);
        batch.setUom(uom);

        lot = new Lot();
        lot.setId(1L);
        lot.setLastActionDate(new Date(System.currentTimeMillis() - 1000000));
        lot.setRemainingQuantity(100.0);
        lot.setUom(uom);

        when(batchManager.findById(batch.getId())).thenReturn(batch);
        when(uomManager.findById(dto.getUomId())).thenReturn(uom);
        when(manager.create(any(Wastage.class))).thenAnswer(invocation -> {
                    Wastage wastage = invocation.getArgument(0);
                    wastage.setId(1L); // Set a valid ID for the wastage
                    return wastage;
                }
        );
        when(lotManager.findById(lot.getId())).thenReturn(lot);
    }


    @Test
    void createBatchWastage_futureDate_throwsValidationException() {
        // Given
        WastageRequestDto dto = new WastageRequestDto();
        dto.setReportedDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // Future date
        Long batchId = 1L;

        ValidationException exception = assertThrows(ValidationException.class, () -> wastageService.createBatchWastage(dto, batchId));
        assertEquals(" Date of Wastage creation cannot be Future or Past Date", exception.getMessage());
    }

    @Test
    void createBatchWastage_PastDateException_ShouldThrowValidationException() {
        dto.setReportedDate(new Date(batch.getDateOfManufacture().getTime() - 1000000));

        Assertions.assertThrows(ValidationException.class, () -> {
            wastageService.createBatchWastage(dto, batch.getId());
        });
    }

    @Test
    void createBatchWastage_validInputs_createsWastageAndUpdatesBatchQuantity() {
        // Given
        WastageRequestDto dto = new WastageRequestDto();
        dto.setReportedDate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        batch.setDateOfManufacture(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2));
        batch.setUom(uom);
        dto.setWastageQuantity(50.0); // Valid wastage quantity
        dto.setUomId(1L);
        Long batchId = 1L;

        Long wastageId = wastageService.createBatchWastage(dto, batchId);
        assertEquals(50.0, batch.getRemainingQuantity(), 0.0);
    }

    @Test
    void createBatchWastage_ValidRequest_ShouldCreateWastage() throws ValidationException {
        Long wastageId = wastageService.createBatchWastage(dto, batch.getId());

        Assertions.assertNotNull(wastageId);
        verify(batchManager, times(1)).update(batch);
    }


    @Test
    void createBatchWastage_QuantityExceededException_ShouldThrowCustomException() {
        dto.setWastageQuantity(1000.0);

        Assertions.assertThrows(CustomException.class, () -> {
            wastageService.createBatchWastage(dto, batch.getId());
        });
    }

    @Test
    void createLotWastage_ValidRequest_ShouldCreateWastage() {
        Long wastageId = wastageService.createLotWastage(dto, lot.getId());

        Assertions.assertNotNull(wastageId);
        verify(lotManager, times(1)).update(lot);
    }

    @Test
    void createLotWastage_FutureDateException_ShouldThrowValidationException() {
        dto.setReportedDate(new Date(System.currentTimeMillis() + 1000000));

        Assertions.assertThrows(ValidationException.class, () -> {
            wastageService.createLotWastage(dto, lot.getId());
        });
    }

    @Test
    void createLotWastage_PastDateException_ShouldThrowValidationException() {
        lot.setDateOfDispatch(new Date(System.currentTimeMillis() - 1000000));
        dto.setReportedDate(new Date(lot.getDateOfDispatch().getTime() - 1000000));

        Assertions.assertThrows(ValidationException.class, () -> {
            wastageService.createLotWastage(dto, lot.getId());
        });
    }

    @Test
    void createLotWastage_QuantityExceededException_ShouldThrowCustomException() {
        dto.setWastageQuantity(1000.0);

        Assertions.assertThrows(CustomException.class, () -> {
            wastageService.createLotWastage(dto, lot.getId());
        });
    }

    @Test
    void createExternalLotWastage_ValidRequest_ShouldCreateWastage() {
        String lotNo = "LOT001";
        List<Lot> lotList = new ArrayList<>();
        lot.setCategory(new Category());
        lot.getCategory().setId(1L);
        lotList.add(lot);

        when(lotManager.findByLotNo(lotNo)).thenReturn(lotList);
        when(uomManager.findByName("Kg")).thenReturn(uom);
        when(manager.create(any(Wastage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ExternalLotDetailsResponseDto response = wastageService.createExternalLotWastage(dto, lotNo);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(lot.getCategory().getId(), response.getCategoryId());
        Assertions.assertEquals(lot.getManufacturerId(), response.getManufacturerId());
        Assertions.assertEquals(lot.getId(), response.getLotId());
        verify(lotManager, times(1)).update(lot);
    }

    @Test
    void createExternalLotWastage_LotNotFound_ShouldThrowCustomException() {
        String lotNo = "LOT001";

        when(lotManager.findByLotNo(lotNo)).thenReturn(new ArrayList<>());

        Assertions.assertThrows(CustomException.class, () -> {
            wastageService.createExternalLotWastage(dto, lotNo);
        });
    }

    @Test
    void getWastageById_ValidId_ShouldReturnWastageDto() {
        Wastage wastage = new Wastage();
        wastage.setId(1L);
        wastage.setWastageQuantity(20.00);
        wastage.setUom(uom);

        when(manager.findById(wastage.getId())).thenReturn(wastage);

        WastageResponseDto response = wastageService.getWastageById(wastage.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(wastage.getId(), response.getId());
    }

    @Test
    void getAllBatchWastages_ValidRequest_ShouldReturnWastageList() {
        List<Wastage> wastages = new ArrayList<>();
        Wastage wastage1 = new Wastage();
        wastage1.setId(1L);
        Wastage wastage2 = new Wastage();
        wastage2.setId(2L);
        wastage1.setWastageQuantity(20.00);
        wastage1.setUom(uom);
        wastage2.setWastageQuantity(20.00);
        wastage2.setUom(uom);
        wastages.add(wastage1);
        wastages.add(wastage2);

        when(manager.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(wastages);

        List<WastageResponseDto> response = wastageService.getAllBatchWastages(0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    void getAllWastesForBatch_ValidRequest_ShouldReturnWastageList() {
        List<Wastage> wastages = new ArrayList<>();
        Wastage wastage1 = new Wastage();
        wastage1.setId(1L);
        wastage1.setBatch(batch);
        Wastage wastage2 = new Wastage();
        wastage2.setId(2L);
        wastage2.setBatch(batch);
        wastage1.setWastageQuantity(20.00);
        wastage1.setUom(uom);
        wastage2.setWastageQuantity(20.00);
        wastage2.setUom(uom);
        wastages.add(wastage1);
        wastages.add(wastage2);

        when(manager.findAllByBatchId(eq(batch.getId()), Mockito.anyInt(), Mockito.anyInt())).thenReturn(wastages);
        when(manager.getCountByBatchId(eq(wastages.size()), eq(batch.getId()), Mockito.anyInt(), Mockito.anyInt())).thenReturn(2L);

        ListResponse<WastageResponseDto> response = wastageService.getAllWastesForBatch(batch.getId(), 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());

    }

    @Test
    void getAllWastesForLot_ValidRequest_ShouldReturnWastageList() {
        List<Wastage> wastages = new ArrayList<>();
        Wastage wastage1 = new Wastage();
        wastage1.setId(1L);
        wastage1.setLot(lot);
        Wastage wastage2 = new Wastage();
        wastage2.setId(2L);
        wastage2.setLot(lot);
        wastage1.setWastageQuantity(20.00);
        wastage1.setUom(uom);
        wastage2.setWastageQuantity(20.00);
        wastage2.setUom(uom);
        wastages.add(wastage1);
        wastages.add(wastage2);

        when(manager.findAllByLotId(eq(lot.getId()), Mockito.anyInt(), Mockito.anyInt())).thenReturn(wastages);
        when(manager.getCountByLotId(eq(wastages.size()), eq(lot.getId()), Mockito.anyInt(), Mockito.anyInt())).thenReturn(2L);

        ListResponse<WastageResponseDto> response = wastageService.getAllWastesForLot(lot.getId(), 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());

    }

    @Test
    void updateBatchWastage_ValidRequest_ShouldUpdateWastage() {
        Wastage existingWastage = new Wastage();
        existingWastage.setId(1L);
        existingWastage.setWastageQuantity(10.0);
        existingWastage.setUom(uom);

        dto.setId(existingWastage.getId());
        dto.setWastageQuantity(20.0);
        dto.setComments("Updated comments");

        when(manager.findById(existingWastage.getId())).thenReturn(existingWastage);

        wastageService.updateBatchWastage(dto, batch.getId());

        Assertions.assertEquals(20.0, existingWastage.getWastageQuantity());
        Assertions.assertEquals("Updated comments", existingWastage.getComments());
        verify(manager, times(1)).update(existingWastage);
        verify(batchManager, times(1)).update(batch);
    }

    @Test
    void updateBatchWastage_QuantityExceededException_ShouldThrowCustomException() {
        Wastage existingWastage = new Wastage();
        existingWastage.setId(1L);
        existingWastage.setWastageQuantity(10.0);
        existingWastage.setUom(uom);

        dto.setId(existingWastage.getId());
        dto.setWastageQuantity(1000.0);

        when(manager.findById(existingWastage.getId())).thenReturn(existingWastage);

        Assertions.assertThrows(CustomException.class, () -> {
            wastageService.updateBatchWastage(dto, batch.getId());
        });
    }

    @Test
    void updateLotWastage_ValidRequest_ShouldUpdateWastage() {
        Wastage existingWastage = new Wastage();
        existingWastage.setId(1L);
        existingWastage.setWastageQuantity(10.0);
        existingWastage.setUom(uom);

        dto.setId(existingWastage.getId());
        dto.setWastageQuantity(20.0);
        dto.setComments("Updated comments");

        when(manager.findById(existingWastage.getId())).thenReturn(existingWastage);

        wastageService.updateLotWastage(dto, lot.getId());

        Assertions.assertEquals(20.0, existingWastage.getWastageQuantity());
        Assertions.assertEquals("Updated comments", existingWastage.getComments());
        verify(manager, times(1)).update(existingWastage);
        verify(lotManager, times(1)).update(lot);
    }

    @Test
    void updateLotWastage_QuantityExceededException_ShouldThrowCustomException() {
        Wastage existingWastage = new Wastage();
        existingWastage.setId(1L);
        existingWastage.setWastageQuantity(10.0);
        existingWastage.setUom(uom);

        dto.setId(existingWastage.getId());
        dto.setWastageQuantity(1000.0);

        when(manager.findById(existingWastage.getId())).thenReturn(existingWastage);

        Assertions.assertThrows(CustomException.class, () -> {
            wastageService.updateLotWastage(dto, lot.getId());
        });
    }

    @Test
    void deleteBatchWastage_ValidRequest_ShouldDeleteWastage() {
        Wastage existingWastage = new Wastage();
        existingWastage.setId(1L);
        existingWastage.setWastageQuantity(10.0);
        existingWastage.setUom(uom);
        existingWastage.setBatch(batch);

        when(manager.findById(existingWastage.getId())).thenReturn(existingWastage);

        wastageService.deleteBatchWastage(existingWastage.getId(), batch.getId());

        verify(manager, times(1)).delete(existingWastage.getId());
    }

    @Test
    void deleteLotWastage_ValidRequest_ShouldDeleteWastage() {
        Wastage existingWastage = new Wastage();
        existingWastage.setId(1L);
        existingWastage.setWastageQuantity(10.0);
        existingWastage.setUom(uom);
        existingWastage.setLot(lot);

        when(manager.findById(existingWastage.getId())).thenReturn(existingWastage);

        wastageService.deleteLotWastage(existingWastage.getId(), lot.getId());

        verify(manager, times(1)).delete(existingWastage.getId());

    }
}


//these tests cover-
//createBatchWastage with valid request, future date exception, past date exception, and quantity exceeded exception.
//createLotWastage with valid request, future date exception, past date exception, and quantity exceeded exception.
//createExternalLotWastage with valid request and lot not found exception.
//getWastageById with a valid ID.
//getAllBatchWastages with a valid request.
//getAllWastesForBatch with a valid request.
//getAllWastesForLot with a valid request.
//updateBatchWastage with a valid request and quantity exceeded exception.
//updateLotWastage with a valid request and quantity exceeded exception.
//deleteBatchWastage with a valid request.
//deleteLotWastage with a valid request.
