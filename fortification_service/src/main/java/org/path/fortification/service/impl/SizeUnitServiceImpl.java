package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.SizeUnitRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.SizeUnitResponseDto;
import org.path.fortification.entity.Batch;
import org.path.fortification.entity.SizeUnit;
import org.path.fortification.entity.UOM;
import org.path.fortification.manager.BatchManager;
import org.path.fortification.manager.SizeUnitManager;
import org.path.fortification.manager.UOMManager;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.SizeUnitMapper;
import org.path.fortification.service.SizeUnitService;
import org.path.parent.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class SizeUnitServiceImpl implements SizeUnitService {

    private final BaseMapper<SizeUnitResponseDto, SizeUnitRequestDto, SizeUnit> mapper = BaseMapper.getForClass(SizeUnitMapper.class);
    private SizeUnitManager manager;
    private BatchManager batchManager;
    private UOMManager uomManager;
    private BatchServiceImpl batchService;
    @Override
    public void createSizeUnit(SizeUnitRequestDto dto) {
        SizeUnit entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public SizeUnitResponseDto getSizeUnitById(Long id) {
        SizeUnit entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<SizeUnitResponseDto> getAllSizeUnits(Long batchId, Integer pageNumber, Integer pageSize) {
        if (!batchService.checkLabAccess(batchId))
            throw new CustomException("Permission denied", HttpStatus.BAD_REQUEST);
        try {
            List<SizeUnit> entities = manager.findAllByBatchId(batchId, pageNumber, pageSize);
            Long count = manager.getCount(entities.size(), batchId, pageNumber, pageSize);
            return ListResponse.from(entities, mapper::toDto, count);
        } catch (Exception ignored) {
            return new ListResponse<>(null, null);
        }
    }

    @Override
    public void updateSizeUnit(SizeUnitRequestDto dto) {
        SizeUnit existingSizeUnit = manager.findById(dto.getId());
        if(dto.getUomId() != null) existingSizeUnit.setUom(new UOM(dto.getUomId()));
        if(dto.getSize() != null) existingSizeUnit.setSize(dto.getSize());
        if(dto.getQuantity() != null) existingSizeUnit.setQuantity(dto.getQuantity());
        manager.update(existingSizeUnit);
    }

    @Override
    public void deleteSizeUnit(Long id) {
        manager.findById(id);
        manager.delete(id);
    }

    @Override
    public boolean createSizeUnits(List<SizeUnitRequestDto> sizeUnits,Long batchId) {
        validateSizeUnits(sizeUnits,batchId);
       int size = sizeUnits.stream().map(s->{
            SizeUnit sizeUnit = mapper.toEntity(s);
          sizeUnit =  manager.create(sizeUnit);
          return sizeUnit.getId();
        }).toList().size();
       return size==sizeUnits.size();
    }

    @Override
    public boolean updateSizeUnits(List<SizeUnitRequestDto> sizeUnits,Long batchId) {
        validateSizeUnits(sizeUnits, batchId);
        List<SizeUnit> existingSizeUnits = manager.findAllByBatchId(batchId, null, null);
        List<SizeUnit> sizeUnitsToDelete = existingSizeUnits.stream().filter(s -> sizeUnits.stream().noneMatch(su -> Objects.equals(su.getId(), s.getId()))).toList();
        sizeUnitsToDelete = sizeUnitsToDelete.stream().filter(s -> !s.getIsDispatched()).collect(Collectors.toList());
        sizeUnitsToDelete.forEach(s -> deleteSizeUnit(s.getId()));
        int size = sizeUnits.stream().map(s -> {
            SizeUnit sizeUnit = mapper.toEntity(s);
            sizeUnit = manager.update(sizeUnit);
            return sizeUnit.getId();
        }).toList().size();
        return size == sizeUnits.size();
    }
    public void validateSizeUnits(List<SizeUnitRequestDto> sizeUnits,Long batchId){
        Batch batch = batchManager.findById(batchId);
        List<Long> uomIds = sizeUnits.stream().map(SizeUnitRequestDto::getUomId).collect(Collectors.toList());
        List<UOM> uoms = uomManager.findAllByIds(uomIds);
        double totalQuantity = sizeUnits
                .stream().mapToDouble(element -> element.getSize()* element.getQuantity()
                        *uoms.stream().filter(uom -> Objects.equals(uom.getId(), element.getUomId()))
                        .findFirst().get().getConversionFactorKg()).sum();
        if (totalQuantity>batch.getRemainingQuantity())throw new CustomException("Quantity exceeds batch total quantity", HttpStatus.BAD_REQUEST);
    }
}
