package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseOrderResponseDtoTest {

    @Test
    void testNoArgsConstructor() {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getSourceManufacturerId()).isNull();
        assertThat(dto.getTargetManufacturerId()).isNull();
        assertThat(dto.getPurchaseOrderId()).isNull();
        assertThat(dto.getMaxDispatchableQuantity()).isNull();
        assertThat(dto.getDispatchedQuantity()).isNull();
        assertThat(dto.getSourceManufacturerLicenseId()).isNull();
        assertThat(dto.getTargetManufacturerLicenseId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto(
                1L, 2L, 3L, "PO123", 100.0, 50.0, "SML123", "TML123"
        );

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getSourceManufacturerId()).isEqualTo(2L);
        assertThat(dto.getTargetManufacturerId()).isEqualTo(3L);
        assertThat(dto.getPurchaseOrderId()).isEqualTo("PO123");
        assertThat(dto.getMaxDispatchableQuantity()).isEqualTo(100.0);
        assertThat(dto.getDispatchedQuantity()).isEqualTo(50.0);
        assertThat(dto.getSourceManufacturerLicenseId()).isEqualTo("SML123");
        assertThat(dto.getTargetManufacturerLicenseId()).isEqualTo("TML123");
    }

    @Test
    void testSettersAndGetters() {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();

        dto.setId(4L);
        dto.setSourceManufacturerId(5L);
        dto.setTargetManufacturerId(6L);
        dto.setPurchaseOrderId("PO456");
        dto.setMaxDispatchableQuantity(200.0);
        dto.setDispatchedQuantity(150.0);
        dto.setSourceManufacturerLicenseId("SML456");
        dto.setTargetManufacturerLicenseId("TML456");

        assertThat(dto.getId()).isEqualTo(4L);
        assertThat(dto.getSourceManufacturerId()).isEqualTo(5L);
        assertThat(dto.getTargetManufacturerId()).isEqualTo(6L);
        assertThat(dto.getPurchaseOrderId()).isEqualTo("PO456");
        assertThat(dto.getMaxDispatchableQuantity()).isEqualTo(200.0);
        assertThat(dto.getDispatchedQuantity()).isEqualTo(150.0);
        assertThat(dto.getSourceManufacturerLicenseId()).isEqualTo("SML456");
        assertThat(dto.getTargetManufacturerLicenseId()).isEqualTo("TML456");
    }

    @Test
    void testToString() {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto(
                7L, 8L, 9L, "PO789", 300.0, 250.0, "SML789", "TML789"
        );

        String dtoString = dto.toString();
        assertThat(dtoString).contains("7");
        assertThat(dtoString).contains("8");
        assertThat(dtoString).contains("9");
        assertThat(dtoString).contains("PO789");
        assertThat(dtoString).contains("300.0");
        assertThat(dtoString).contains("250.0");
        assertThat(dtoString).contains("SML789");
        assertThat(dtoString).contains("TML789");
    }
}
