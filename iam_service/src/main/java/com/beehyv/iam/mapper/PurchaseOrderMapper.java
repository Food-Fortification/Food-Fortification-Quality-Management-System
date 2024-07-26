package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.external.PurchaseOrderRequestDto;
import com.beehyv.iam.dto.external.PurchaseOrderResponseDto;
import com.beehyv.iam.model.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PurchaseOrderMapper implements Mappable<PurchaseOrderResponseDto, PurchaseOrderRequestDto, PurchaseOrder> {

    @Override
    public PurchaseOrderResponseDto toDto(PurchaseOrder entity) {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        return dto;
    }

    @Override
    public PurchaseOrder toEntity(PurchaseOrderRequestDto dto) {
        return null;
    }



}
