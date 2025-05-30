package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.WastageRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.WastageResponseDto;
import org.path.fortification.entity.Batch;
import org.path.fortification.entity.Lot;
import org.path.fortification.entity.UOM;
import org.path.fortification.entity.Wastage;
import org.path.fortification.manager.BatchManager;
import org.path.fortification.manager.BatchWastageManager;
import org.path.fortification.manager.LotManager;
import org.path.fortification.manager.UOMManager;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.WastageMapper;
import org.path.fortification.service.WastageService;
import org.path.parent.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class WastageServiceImpl implements WastageService {
    private final BaseMapper<WastageResponseDto, WastageRequestDto, Wastage> mapper = BaseMapper.getForClass(WastageMapper.class);
    private BatchWastageManager manager;
    private BatchManager batchManager;
    private LotManager lotManager;
    private UOMManager uomManager;


    @Override
    public Long createBatchWastage(WastageRequestDto dto, Long batchId) {
        Batch batch = batchManager.findById(batchId);
        if(dto.getReportedDate().after(new Date()) || dto.getReportedDate().before(batch.getDateOfManufacture())){
            throw new ValidationException(" Date of Wastage creation cannot be Future or Past Date");
        }
        UOM uom = uomManager.findById(dto.getUomId());
        double wastageQuantity = dto.getWastageQuantity()*uom.getConversionFactorKg();
        if (batch.getRemainingQuantity()*batch.getUom().getConversionFactorKg()<wastageQuantity)throw new CustomException("Wastage cannot be more than remaining Quantity", HttpStatus.BAD_REQUEST);
        Wastage entity = mapper.toEntity(dto);
        entity = manager.create(entity);
        double remainingBatchQuantity = batch.getRemainingQuantity()-wastageQuantity;
        batch.setRemainingQuantity(remainingBatchQuantity/batch.getUom().getConversionFactorKg());
        batchManager.update(batch);
        return entity.getId();
    }
    @Override
    public Long createLotWastage(WastageRequestDto dto, Long lotId) {
        Lot lot = lotManager.findById(lotId);
        if (dto.getReportedDate().after(new Date()) || dto.getReportedDate().before(lot.getDateOfDispatch() == null ? lot.getLastActionDate() : lot.getDateOfDispatch())) {
            throw new ValidationException(" Date of Wastage creation cannot be Future or Past Date");
        }
        UOM uom = uomManager.findById(dto.getUomId());
        double wastageQuantity = dto.getWastageQuantity()*uom.getConversionFactorKg();
        if (lot.getRemainingQuantity()*lot.getUom().getConversionFactorKg()<wastageQuantity)throw new CustomException("Wastage cannot be more than remaining Quantity", HttpStatus.BAD_REQUEST);
        Wastage entity = mapper.toEntity(dto);
        entity = manager.create(entity);
        double remainingBatchQuantity = lot.getRemainingQuantity()-wastageQuantity;
        lot.setRemainingQuantity(remainingBatchQuantity/lot.getUom().getConversionFactorKg());
        lotManager.update(lot);
        return entity.getId();
    }

    @Override
    public WastageResponseDto getWastageById(Long id) {
        Wastage entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public List<WastageResponseDto> getAllBatchWastages(Integer pageNumber, Integer pageSize) {
        List<Wastage> entities = manager.findAll(pageNumber, pageSize);
        return entities.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ListResponse<WastageResponseDto> getAllWastesForBatch(Long batchId, Integer pageNumber, Integer pageSize) {
        List<Wastage> entities = manager.findAllByBatchId(batchId, pageNumber, pageSize);
        Long count = manager.getCountByBatchId(entities.size(), batchId, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public ListResponse<WastageResponseDto> getAllWastesForLot(Long lotId, Integer pageNumber, Integer pageSize) {
        List<Wastage> entities = manager.findAllByLotId(lotId, pageNumber, pageSize);
        Long count = manager.getCountByLotId(entities.size(), lotId, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateBatchWastage(WastageRequestDto dto, Long batchId) {
        Wastage existingWastage = manager.findById(dto.getId());
        UOM uom = uomManager.findById(dto.getUomId());
        Batch batch = batchManager.findById(batchId);
        double diffQuantity = existingWastage.getWastageQuantity()* existingWastage.getUom().getConversionFactorKg() - dto.getWastageQuantity()*uom.getConversionFactorKg();
        if (diffQuantity==0d){
            return;
        }
        double quantityCheck = diffQuantity
                +batch.getRemainingQuantity()*batch.getUom().getConversionFactorKg() ;
        if (quantityCheck<0)throw new CustomException("Wastage cannot be more than remaining Quantity", HttpStatus.BAD_REQUEST);
        existingWastage.setWastageQuantity(dto.getWastageQuantity());
        if (dto.getComments() != null) {
            existingWastage.setComments(dto.getComments());
        }
        if (dto.getReportedDate() != null) {
            existingWastage.setReportedDate(dto.getReportedDate());
        }
        if (dto.getUomId() != null) {
            existingWastage.setUom(new UOM(dto.getUomId()));
        }
        batch.setRemainingQuantity(quantityCheck/batch.getUom().getConversionFactorKg());
        batchManager.update(batch);
        manager.update(existingWastage);
    }

    @Override
    public void updateLotWastage(WastageRequestDto dto, Long lotId) {
        Wastage existingWastage = manager.findById(dto.getId());
        UOM uom = uomManager.findById(dto.getUomId());
        Lot lot = lotManager.findById(lotId);
        double diffQuantity = existingWastage.getWastageQuantity()* existingWastage.getUom().getConversionFactorKg() - dto.getWastageQuantity()*uom.getConversionFactorKg();
        if (diffQuantity==0d){
            return;
        }
        double quantityCheck = diffQuantity
                +lot.getRemainingQuantity()*lot.getUom().getConversionFactorKg() ;
        if (quantityCheck<0)throw new CustomException("Wastage cannot be more than remaining Quantity", HttpStatus.BAD_REQUEST);
        existingWastage.setWastageQuantity(dto.getWastageQuantity());
        if (dto.getComments() != null) {
            existingWastage.setComments(dto.getComments());
        }
        if (dto.getReportedDate() != null) {
            existingWastage.setReportedDate(dto.getReportedDate());
        }
        if (dto.getUomId() != null) {
            existingWastage.setUom(new UOM(dto.getUomId()));
        }
        lot.setRemainingQuantity(quantityCheck/lot.getUom().getConversionFactorKg());
        lotManager.update(lot);
        manager.update(existingWastage);
    }

    @Override
    public void deleteBatchWastage(Long id,Long batchId) {
        Wastage existingWastage = manager.findById(id);
        Batch batch = batchManager.findById(batchId);
        double updatedQuantity = batch.getRemainingQuantity()*batch.getUom().getConversionFactorKg()+ existingWastage.getWastageQuantity()
                * existingWastage.getUom().getConversionFactorKg();
        batch.setRemainingQuantity(updatedQuantity
                /batch.getUom().getConversionFactorKg());
        manager.delete(id);
    }

    @Override
    public void deleteLotWastage(Long id,Long lotId) {
        Wastage existingWastage = manager.findById(id);
        Lot lot = lotManager.findById(lotId);
        double updatedQuantity = lot.getRemainingQuantity()*lot.getUom().getConversionFactorKg()+ existingWastage.getWastageQuantity()
                * existingWastage.getUom().getConversionFactorKg();
        lot.setRemainingQuantity(updatedQuantity
                /lot.getUom().getConversionFactorKg());
        manager.delete(id);
    }
}
