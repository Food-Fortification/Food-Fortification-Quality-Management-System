package org.path.iam.manager;

import org.path.iam.dao.PurchaseOrderDao;
import org.path.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import org.path.iam.dto.responseDto.ManufacturerDispatchableQuantityResponseDto;
import org.path.iam.model.PurchaseOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PurchaseOrderManagerTest {

    @Mock
    private PurchaseOrderDao purchaseOrderDao;

    @InjectMocks
    private PurchaseOrderManager purchaseOrderManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBySourceTargetMapping() {
        // Prepare test data
        Long sourceManufacturerId = 1L;
        Long targetManufacturerId = 2L;
        List<PurchaseOrder> expectedPurchaseOrders = new ArrayList<>(); // Add some dummy PurchaseOrder objects

        // Mock behavior of DAO
        when(purchaseOrderDao.findBySourceTargetMapping(sourceManufacturerId, targetManufacturerId)).thenReturn(expectedPurchaseOrders);

        // Call method under test
        List<PurchaseOrder> actualPurchaseOrders = purchaseOrderManager.findBySourceTargetMapping(sourceManufacturerId, targetManufacturerId);

        // Verify
        assertEquals(expectedPurchaseOrders, actualPurchaseOrders);
        verify(purchaseOrderDao, times(1)).findBySourceTargetMapping(sourceManufacturerId, targetManufacturerId);
    }

    @Test
    public void testFindExistingPurchaseOrder() {
        String purchaseOrderId = "PO123";
        when(purchaseOrderDao.findExistingPurchaseOrder(purchaseOrderId)).thenReturn(true);

        boolean result = purchaseOrderManager.findExistingPurchaseOrder(purchaseOrderId);

        assertTrue(result);
    }

    @Test
    public void testFindByPurchaseOrderIds() {
        String purchaseOrderId = "PO123";
        String childPurchaseOrderId = "ChildPO123";
        PurchaseOrder expectedPurchaseOrder = new PurchaseOrder();
        when(purchaseOrderDao.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId)).thenReturn(expectedPurchaseOrder);

        PurchaseOrder actualPurchaseOrder = purchaseOrderManager.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId);

        assertEquals(expectedPurchaseOrder, actualPurchaseOrder);
    }

    @Test
    public void testGetManufacturerDispatchableQuantityAggregate() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        List<ManufacturerDispatchableQuantityResponseDto> expectedList = new ArrayList<>();
        when(purchaseOrderDao.getManufacturerDispatchableQuantityAggregate(dto)).thenReturn(expectedList);

        List<ManufacturerDispatchableQuantityResponseDto> actualList = purchaseOrderManager.getManufacturerDispatchableQuantityAggregate(dto);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetDispatchableQuantityByDistrictGeoId() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        List<ManufacturerDispatchableQuantityResponseDto> expectedList = new ArrayList<>();
        when(purchaseOrderDao.getDispatchableQuantityByDistrictGeoId(dto)).thenReturn(expectedList);

        List<ManufacturerDispatchableQuantityResponseDto> actualList = purchaseOrderManager.getDispatchableQuantityByDistrictGeoId(dto);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetDispatchableQuantityByDistrictGeoIdForTarget() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        List<ManufacturerDispatchableQuantityResponseDto> expectedList = new ArrayList<>();
        when(purchaseOrderDao.getDispatchableQuantityByDistrictGeoIdForTarget(dto)).thenReturn(expectedList);

        List<ManufacturerDispatchableQuantityResponseDto> actualList = purchaseOrderManager.getDispatchableQuantityByDistrictGeoIdForTarget(dto);

        assertEquals(expectedList, actualList);
    }
    // Write similar test methods for other methods in PurchaseOrderManager
}
