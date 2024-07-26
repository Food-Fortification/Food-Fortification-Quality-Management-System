package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.external.PurchaseOrderRequestDto;
import com.beehyv.iam.dto.external.PurchaseOrderResponseDto;
import com.beehyv.iam.model.PurchaseOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PurchaseOrderMapperTest {

    @Test
    void testToDto() {
        // Create a PurchaseOrder entity
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderId("12345L");

        // Create a PurchaseOrderMapper instance
        PurchaseOrderMapper purchaseOrderMapper = new PurchaseOrderMapper();

        // Perform mapping
        PurchaseOrderResponseDto responseDto = purchaseOrderMapper.toDto(purchaseOrder);

        // Assert
        assertEquals("12345L", responseDto.getPurchaseOrderId());
    }

    @Test
    void testToEntity() {
        // Create a PurchaseOrderRequestDto
        PurchaseOrderRequestDto requestDto = new PurchaseOrderRequestDto();
        // As the toEntity method returns null, we don't need to set anything in the requestDto.

        // Create a PurchaseOrderMapper instance
        PurchaseOrderMapper purchaseOrderMapper = new PurchaseOrderMapper();

        // Perform mapping
        PurchaseOrder entity = purchaseOrderMapper.toEntity(requestDto);

        // Assert
        assertNull(entity); // In the current implementation, the method always returns null.
    }
}
