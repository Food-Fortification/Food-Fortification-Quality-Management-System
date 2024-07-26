package com.beehyv.iam.dto.external;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseOrderRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getMoTransactionId()).isNull();
        assertThat(dto.getManufacturingid()).isNull();
        assertThat(dto.getDistrictId()).isNull();
        assertThat(dto.getTotalAllotedQty()).isNull();
        assertThat(dto.getAllotedMonth()).isNull();
        assertThat(dto.getDmOrderNo()).isNull();
        assertThat(dto.getDmorderDate()).isNull();
        assertThat(dto.getDmDeliverDate()).isNull();
        assertThat(dto.getCommodityId()).isNull();
        assertThat(dto.getExternalMetaDataRequestDtos()).isNotNull().isEmpty();
        assertThat(dto.getBufferGodownDetails()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Set<ExternalMetaDataRequestDto> externalMetaData = new HashSet<>();
        Set<BufferGodownDetailRequestDto> bufferGodownDetails = new HashSet<>();
        Date orderDate = new Date();
        Date deliverDate = new Date();

        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto(
                1L, "tx123", "manu123", "dist123", 100.0, 6,
                "dm123", orderDate, deliverDate, "com123",
                externalMetaData, bufferGodownDetails
        );

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMoTransactionId()).isEqualTo("tx123");
        assertThat(dto.getManufacturingid()).isEqualTo("manu123");
        assertThat(dto.getDistrictId()).isEqualTo("dist123");
        assertThat(dto.getTotalAllotedQty()).isEqualTo(100.0);
        assertThat(dto.getAllotedMonth()).isEqualTo(6);
        assertThat(dto.getDmOrderNo()).isEqualTo("dm123");
        assertThat(dto.getDmorderDate()).isEqualTo(orderDate);
        assertThat(dto.getDmDeliverDate()).isEqualTo(deliverDate);
        assertThat(dto.getCommodityId()).isEqualTo("com123");
        assertThat(dto.getExternalMetaDataRequestDtos()).isEqualTo(externalMetaData);
        assertThat(dto.getBufferGodownDetails()).isEqualTo(bufferGodownDetails);
    }

    @Test
    void testSettersAndGetters() {
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        Date orderDate = new Date();
        Date deliverDate = new Date();
        Set<ExternalMetaDataRequestDto> externalMetaData = new HashSet<>();
        Set<BufferGodownDetailRequestDto> bufferGodownDetails = new HashSet<>();

        dto.setId(2L);
        dto.setMoTransactionId("tx456");
        dto.setManufacturingid("manu456");
        dto.setDistrictId("dist456");
        dto.setTotalAllotedQty(200.0);
        dto.setAllotedMonth(7);
        dto.setDmOrderNo("dm456");
        dto.setDmorderDate(orderDate);
        dto.setDmDeliverDate(deliverDate);
        dto.setCommodityId("com456");
        dto.setExternalMetaDataRequestDtos(externalMetaData);
        dto.setBufferGodownDetails(bufferGodownDetails);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getMoTransactionId()).isEqualTo("tx456");
        assertThat(dto.getManufacturingid()).isEqualTo("manu456");
        assertThat(dto.getDistrictId()).isEqualTo("dist456");
        assertThat(dto.getTotalAllotedQty()).isEqualTo(200.0);
        assertThat(dto.getAllotedMonth()).isEqualTo(7);
        assertThat(dto.getDmOrderNo()).isEqualTo("dm456");
        assertThat(dto.getDmorderDate()).isEqualTo(orderDate);
        assertThat(dto.getDmDeliverDate()).isEqualTo(deliverDate);
        assertThat(dto.getCommodityId()).isEqualTo("com456");
        assertThat(dto.getExternalMetaDataRequestDtos()).isEqualTo(externalMetaData);
        assertThat(dto.getBufferGodownDetails()).isEqualTo(bufferGodownDetails);
    }

    @Test
    void testToString() {
        Date orderDate = new Date();
        Date deliverDate = new Date();
        Set<ExternalMetaDataRequestDto> externalMetaData = new HashSet<>();
        Set<BufferGodownDetailRequestDto> bufferGodownDetails = new HashSet<>();

        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto(
                3L, "tx789", "manu789", "dist789", 300.0, 8,
                "dm789", orderDate, deliverDate, "com789",
                externalMetaData, bufferGodownDetails
        );

        String dtoString = dto.toString();
        assertThat(dtoString).contains("3");
        assertThat(dtoString).contains("tx789");
        assertThat(dtoString).contains("manu789");
        assertThat(dtoString).contains("dist789");
        assertThat(dtoString).contains("300.0");
        assertThat(dtoString).contains("8");
        assertThat(dtoString).contains("dm789");
        assertThat(dtoString).contains(orderDate.toString());
        assertThat(dtoString).contains(deliverDate.toString());
        assertThat(dtoString).contains("com789");
        assertThat(dtoString).contains(externalMetaData.toString());
        assertThat(dtoString).contains(bufferGodownDetails.toString());
    }
}
