package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.*;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.*;
import org.path.fortification.dto.requestDto.*;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.*;

public class MixMappingMapper implements Mappable<MixMappingResponseDto, MixMappingRequestDto, MixMapping> {
    private final BaseMapper<BatchListResponseDTO, BatchRequestDto, Batch> batchListMapper = BaseMapper.getForListClass(BatchMapper.class);
    private static final BaseMapper<LotListResponseDTO, LotRequestDto, Lot> lotListMapper = BaseMapper.getForListClass(LotMapper.class);
    private final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> uomMapper = BaseMapper.getForClass(UOMMapper.class);
    private final BaseMapper<CategoryResponseDto, CategoryRequestDto, Category> categoryMapper = BaseMapper.getForClass(CategoryMapper.class);
    // Convert User JPA Entity into MixMappingDto
    @Override
    public MixMappingResponseDto toDto(MixMapping entity) {
        if(entity == null) return null;
        MixMappingResponseDto dto = new MixMappingResponseDto();
        dto.setId(entity.getId());
        dto.setQuantityUsed(entity.getQuantityUsed());
        if(entity.getUom() != null) dto.setUom(uomMapper.toDto(entity.getUom()));
        if(entity.getSourceLot() != null) dto.setSourceLot(lotListMapper.toListDTO(entity.getSourceLot()));
        if(entity.getTargetBatch() != null) dto.setTargetBatch(batchListMapper.toListDTO(entity.getTargetBatch()));
        if (entity.getSourceLot() != null) {
            dto.setSourceCategory(categoryMapper.toDto(entity.getSourceLot().getCategory()));
        }
        return dto;
    }

    // Convert MixMappingDto into MixMapping JPA Entity
    @Override
    public MixMapping toEntity(MixMappingRequestDto dto) {
        MixMapping entity = new MixMapping();
        if(dto.getSourceLotId() != null) {
            entity.setSourceLot(new Lot(dto.getSourceLotId()));
        }
        if(dto.getUomId() != null) {
            entity.setUom(new UOM(dto.getUomId()));
        }
        entity.setId(dto.getId());
        entity.setQuantityUsed(dto.getQuantityUsed());
        return entity;
    }
}
