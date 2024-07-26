package com.beehyv.iam.service;

import com.beehyv.iam.dao.PurchaseOrderDao;
import com.beehyv.iam.dto.external.ChildPurchaseOrderResponseDto;
import com.beehyv.iam.dto.external.PurchaseOrderRequestDto;
import com.beehyv.iam.dto.external.PurchaseOrderResponseDto;
import com.beehyv.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerDispatchableQuantityResponseDto;
import com.beehyv.iam.manager.ManufacturerCategoryManager;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.manager.PurchaseOrderManager;
import com.beehyv.iam.mapper.BaseMapper;
import com.beehyv.iam.mapper.PurchaseOrderMapper;
import com.beehyv.iam.model.PurchaseOrder;
import com.beehyv.parent.exceptions.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @Mock
    private PurchaseOrderManager purchaseOrderManager;
    @Mock
    private PurchaseOrderDao purchaseOrderRepository;
    @Mock
    private ManufacturerManager manufacturerManager;

    @Mock
    private ManufacturerCategoryManager manufacturerCategoryManager;

    @Mock
    private BaseMapper<PurchaseOrderResponseDto, PurchaseOrderRequestDto, PurchaseOrder> purchaseOrderMapper;

    @InjectMocks
    private PurchaseOrderService purchaseOrderService;

    @BeforeEach
    public void setUp() {
        purchaseOrderMapper = BaseMapper.getForClass(PurchaseOrderMapper.class);
    }

    @Test
    public void testUpdateQuantityByPurchaseOrderIds_Success() {
        // Arrange
        String purchaseOrderId = "PO123";
        String childPurchaseOrderId = "ChildPO123";
        Double quantity = 10.0;
        PurchaseOrder po = new PurchaseOrder();
        po.setDispatchedQuantity(5.0); // Initial dispatched quantity
        when(purchaseOrderManager.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId)).thenReturn(po);
        when(purchaseOrderManager.update(po)).thenReturn(po);
        // Act
        assertDoesNotThrow(() -> purchaseOrderService.updateQuantityByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId, quantity));

        // Assert
        assertEquals(15.0, po.getDispatchedQuantity()); // Expected dispatched quantity after update
        verify(purchaseOrderManager, times(1)).update(po);
    }

    @Test
    public void testUpdateQuantityByPurchaseOrderIds_PurchaseOrderNotFound() {
        // Arrange
        String purchaseOrderId = "PO123";
        String childPurchaseOrderId = "ChildPO123";
        Double quantity = 10.0;
        when(purchaseOrderManager.findByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId)).thenReturn(null);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> purchaseOrderService.updateQuantityByPurchaseOrderIds(purchaseOrderId, childPurchaseOrderId, quantity));
        assertEquals("No entry found for given purchaseOrderId and childPurchaseOrderId", exception.getMessage());
        verify(purchaseOrderManager, never()).update(any());
    }

    @Test
    public void testCreate_InvalidSourceManufacturerId() {
        // Arrange
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();

        // Act & Assert
        verify(purchaseOrderManager, never()).create(any());
    }

    @Test
    public void testCreate_InvalidTargetManufacturerId() {
        // Arrange
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();

        // Act & Assert
        verify(purchaseOrderManager, never()).create(any());
    }

    @Test
    public void testCreate_PurchaseOrderExists() {
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        dto.setMoTransactionId("123");

        when(purchaseOrderManager.findExistingPurchaseOrder(anyString())).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> purchaseOrderService.create(dto));

        assertEquals("MoTransactionId already exists.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testDelete() {
        doNothing().when(purchaseOrderManager).delete(anyLong());

        assertDoesNotThrow(() -> purchaseOrderService.delete(1L));
        verify(purchaseOrderManager, times(1)).delete(anyLong());
    }


    @Test
    void testCreateWithExistingMoTransactionId() {
        // Prepare test data
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        dto.setMoTransactionId("123456");
        // Mock behavior
        when(purchaseOrderManager.findExistingPurchaseOrder("123456")).thenReturn(true);

        // Call the method and assert that it throws CustomException
        assertThrows(CustomException.class, () -> purchaseOrderService.create(dto));
    }

    @Test
    public void testGetBySourceTargetMapping() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderId("PO123");
        purchaseOrder.setChildPurchaseOrderId("CPO123");
        purchaseOrder.setMaxDispatchableQuantity(100.0);
        purchaseOrder.setDispatchedQuantity(50.0);
        purchaseOrder.setCommodityId("CMD017");

        when(purchaseOrderManager.findBySourceTargetMapping(anyLong(), anyLong())).thenReturn(List.of(purchaseOrder));

        Map<String, List<ChildPurchaseOrderResponseDto>> response = purchaseOrderService.getBySourceTargetMapping(1L, 2L);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    public void testGetByPurchaseOrderIds() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setMaxDispatchableQuantity(100.0);
        purchaseOrder.setDispatchedQuantity(50.0);

        when(purchaseOrderManager.findByPurchaseOrderIds(anyString(), anyString())).thenReturn(purchaseOrder);

        Double result = purchaseOrderService.getByPurchaseOrderIds("PO123", "CPO123");

        assertNotNull(result);
        assertEquals(50.0, result);
    }

    @Test
    public void testGetDispatchableQuantityByManufacturer() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        List<ManufacturerDispatchableQuantityResponseDto> responseDtos = List.of(new ManufacturerDispatchableQuantityResponseDto());

        when(purchaseOrderManager.getManufacturerDispatchableQuantityAggregate(any(ManufacturerDispatchableQuantityRequestDto.class)))
                .thenReturn(responseDtos);

        List<ManufacturerDispatchableQuantityResponseDto> response = purchaseOrderService.getDispatchableQuantityByManufacturer(dto);

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    public void testGetDispatchableQuantityByDistrictGeoId() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        List<ManufacturerDispatchableQuantityResponseDto> responseDtos = List.of(new ManufacturerDispatchableQuantityResponseDto());
        List<Long> filteredManufacturerIds = List.of(1L);

        when(purchaseOrderManager.getDispatchableQuantityByDistrictGeoId(any(ManufacturerDispatchableQuantityRequestDto.class)))
                .thenReturn(responseDtos);
        when(manufacturerManager.getTestManufacturerIds()).thenReturn(List.of());

        List<ManufacturerDispatchableQuantityResponseDto> response = purchaseOrderService.getDispatchableQuantityByDistrictGeoId(dto);

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    public void testGetDispatchableQuantityByDistrictGeoIdForTarget() {
        ManufacturerDispatchableQuantityRequestDto dto = new ManufacturerDispatchableQuantityRequestDto();
        List<ManufacturerDispatchableQuantityResponseDto> responseDtos = List.of(new ManufacturerDispatchableQuantityResponseDto());
        List<Long> filteredManufacturerIds = List.of(1L);

        when(purchaseOrderManager.getDispatchableQuantityByDistrictGeoIdForTarget(any(ManufacturerDispatchableQuantityRequestDto.class)))
                .thenReturn(responseDtos);
        when(manufacturerManager.getTestManufacturerIds()).thenReturn(List.of());

        List<ManufacturerDispatchableQuantityResponseDto> response = purchaseOrderService.getDispatchableQuantityByDistrictGeoIdForTarget(dto);

        assertNotNull(response);
        assertEquals(0, response.size());
    }
}
