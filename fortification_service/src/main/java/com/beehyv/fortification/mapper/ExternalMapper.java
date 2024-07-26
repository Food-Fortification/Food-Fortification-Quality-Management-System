package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.external.BatchExternalRequestDto;
import com.beehyv.fortification.dto.requestDto.LotRequestDto;
import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.Lot;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ExternalMapper {

    public static Batch mapBatchDtoToEntityExternal(BatchExternalRequestDto dto, Category category, Long manufacturerId){
        Batch entity = new Batch();
        entity.setManufacturerId(manufacturerId);
        entity.setCategory(category);
        entity.setComments(dto.getComments());
        entity.setDateOfManufacture(dto.getDateOfManufacture());

        Date doe = dto.getDateOfManufacture();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(doe);
        calendar.add(Calendar.YEAR, 2);
        Date updatedDoe = calendar.getTime();

        entity.setDateOfExpiry(updatedDoe);
        entity.setTotalQuantity(dto.getTotalQuantity());
        entity.setRemainingQuantity(dto.getTotalQuantity());
        entity.setLastActionDate(dto.getDateOfManufacture());
        return entity;
    }

    public static Lot mapLotDtoToEntityExternal(BatchExternalRequestDto dto, Batch batch, Long targetManufacturerId){
        Lot lot = new Lot();
        lot.setUom(batch.getUom());
        lot.setIsReceivedAtTarget(true);
        lot.setDateOfReceiving(dto.getDateOfAcceptance());
        lot.setDateOfAcceptance(dto.getDateOfAcceptance());
        lot.setTargetManufacturerId(targetManufacturerId);
        lot.setIsTargetAccepted(true);
        lot.setIsTargetAcknowledgedReport(true);
        lot.setTotalQuantity(batch.getTotalQuantity());
        lot.setRemainingQuantity(batch.getRemainingQuantity());
        lot.setManufacturerId(batch.getManufacturerId());
        lot.setCategory(batch.getCategory());
        lot.setLotNo(batch.getBatchNo() + "_L01");
        lot.setLastActionDate(dto.getDateOfAcceptance());
        return lot;
    }

    public static LotRequestDto mapLotExternalDtoToRequestDto(Batch batch, Long targetManufacturerId, SizeUnitRequestDto sizeUnitRequestDto){
        LotRequestDto lotRequestDto = new LotRequestDto();
        lotRequestDto.setBatchId(batch.getId());
        lotRequestDto.setComments(batch.getComments());
        lotRequestDto.setDateOfDispatch(batch.getDateOfManufacture());
        lotRequestDto.setTargetManufacturerId(targetManufacturerId);
        lotRequestDto.setSizeUnits(Collections.singleton(sizeUnitRequestDto));
        return lotRequestDto;
    }
}
