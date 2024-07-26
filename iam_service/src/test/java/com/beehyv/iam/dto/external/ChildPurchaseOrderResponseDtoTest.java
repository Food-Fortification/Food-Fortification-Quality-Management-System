package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChildPurchaseOrderResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        ChildPurchaseOrderResponseDto dto = new ChildPurchaseOrderResponseDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getChildPurchaseOrderId()).isNull();
        assertThat(dto.getMaxDispatchableQuantity()).isNull();
        assertThat(dto.getCommodityId()).isNull();
        assertThat(dto.getCommodityType()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        ChildPurchaseOrderResponseDto dto = new ChildPurchaseOrderResponseDto("PO123", 150.0, "COM456", "TYPE789");
        assertThat(dto).isNotNull();
        assertThat(dto.getChildPurchaseOrderId()).isEqualTo("PO123");
        assertThat(dto.getMaxDispatchableQuantity()).isEqualTo(150.0);
        assertThat(dto.getCommodityId()).isEqualTo("COM456");
        assertThat(dto.getCommodityType()).isEqualTo("TYPE789");
    }

    @Test
    void testSettersAndGetters() {
        ChildPurchaseOrderResponseDto dto = new ChildPurchaseOrderResponseDto();
        dto.setChildPurchaseOrderId("PO123");
        dto.setMaxDispatchableQuantity(150.0);
        dto.setCommodityId("COM456");
        dto.setCommodityType("TYPE789");

        assertThat(dto.getChildPurchaseOrderId()).isEqualTo("PO123");
        assertThat(dto.getMaxDispatchableQuantity()).isEqualTo(150.0);
        assertThat(dto.getCommodityId()).isEqualTo("COM456");
        assertThat(dto.getCommodityType()).isEqualTo("TYPE789");
    }

    @Test
    void testToString() {
        ChildPurchaseOrderResponseDto dto = new ChildPurchaseOrderResponseDto("PO123", 150.0, "COM456", "TYPE789");
        String expectedString = "ChildPurchaseOrderResponseDto(childPurchaseOrderId=PO123, maxDispatchableQuantity=150.0, commodityId=COM456, commodityType=TYPE789)";
        assertThat(dto.toString()).isEqualTo(expectedString);
    }

    @Test
    void testEqualsAndHashCode() {
        ChildPurchaseOrderResponseDto dto1 = new ChildPurchaseOrderResponseDto("PO123", 150.0, "COM456", "TYPE789");
        ChildPurchaseOrderResponseDto dto2 = new ChildPurchaseOrderResponseDto("PO123", 150.0, "COM456", "TYPE789");
        ChildPurchaseOrderResponseDto dto3 = new ChildPurchaseOrderResponseDto("PO124", 160.0, "COM457", "TYPE790");

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }
}
