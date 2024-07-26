package com.beehyv.iam.controller;

import com.beehyv.iam.dto.external.ChildPurchaseOrderResponseDto;
import com.beehyv.iam.dto.external.PurchaseOrderRequestDto;
import com.beehyv.iam.dto.external.PurchaseOrderResponseDto;
import com.beehyv.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerDispatchableQuantityResponseDto;
import com.beehyv.iam.service.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PurchaseOrderControllerTest {

    @Mock
    private PurchaseOrderService purchaseOrderService;

    @InjectMocks
    private PurchaseOrderController purchaseOrderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        doNothing().when(purchaseOrderService).create(any(PurchaseOrderRequestDto.class));

        ResponseEntity<?> response = purchaseOrderController.create(new PurchaseOrderRequestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created", response.getBody());
    }

    @Test
    void testUpdate() {
        doNothing().when(purchaseOrderService).update(any(PurchaseOrderRequestDto.class));

        ResponseEntity<?> response = purchaseOrderController.update(1L, new PurchaseOrderRequestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(purchaseOrderService).delete(anyLong());

        ResponseEntity<?> response = purchaseOrderController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById() {
        PurchaseOrderResponseDto responseDto = new PurchaseOrderResponseDto();
        when(purchaseOrderService.getById(anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = purchaseOrderController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetBySourceTargetMapping() {
        Map<String, List<ChildPurchaseOrderResponseDto>> responseDto = new HashMap<>();
        when(purchaseOrderService.getBySourceTargetMapping(anyLong(), anyLong())).thenReturn(responseDto);

        ResponseEntity<?> response = purchaseOrderController.getBySourceTargetMapping(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetByPurchaseOrderIds() {
        Object responseDto = new Object();
        when(purchaseOrderService.getByPurchaseOrderIds(anyString(), anyString())).thenReturn(1.0);

        ResponseEntity<?> response = purchaseOrderController.getByPurchaseOrderIds("order1", "order2");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateQuantityByPurchaseOrderIds() {
        doNothing().when(purchaseOrderService).updateQuantityByPurchaseOrderIds(anyString(), anyString(), anyDouble());

        ResponseEntity<?> response = purchaseOrderController.updateQuantityByPurchaseOrderIds("order1", "order2", 100.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully Updated", response.getBody());
    }

    @Test
    void testGetDispatchableQuantityByManufacturer() {
        List<ManufacturerDispatchableQuantityResponseDto> responseDto = new ArrayList<>();
        when(purchaseOrderService.getDispatchableQuantityByManufacturer(any(ManufacturerDispatchableQuantityRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<?> response = purchaseOrderController.getDispatchableQuantityByManufacturer(new ManufacturerDispatchableQuantityRequestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetDispatchableQuantityByDistrictGeoId() {
        List<ManufacturerDispatchableQuantityResponseDto> responseDto = new ArrayList<>();
        when(purchaseOrderService.getDispatchableQuantityByDistrictGeoId(any(ManufacturerDispatchableQuantityRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<?> response = purchaseOrderController.getDispatchableQuantityByDistrictGeoId(new ManufacturerDispatchableQuantityRequestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testGetDispatchableQuantityByDistrictGeoIdForTarget() {
        List<ManufacturerDispatchableQuantityResponseDto> responseDto = new ArrayList<>();
        when(purchaseOrderService.getDispatchableQuantityByDistrictGeoIdForTarget(any(ManufacturerDispatchableQuantityRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<?> response = purchaseOrderController.getDispatchableQuantityByDistrictGeoIdForTarget(new ManufacturerDispatchableQuantityRequestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}
