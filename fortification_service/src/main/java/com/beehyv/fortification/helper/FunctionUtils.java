package com.beehyv.fortification.helper;

import com.beehyv.fortification.dto.requestDto.BatchRequestDto;
import com.beehyv.fortification.dto.requestDto.LotRequestDto;
import com.beehyv.fortification.dto.requestDto.MixMappingRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.BatchLotMapping;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.MixMapping;
import com.beehyv.fortification.enums.ManufacturerCategoryAction;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.BatchMapper;
import com.beehyv.fortification.mapper.LotMapper;
import com.beehyv.fortification.mapper.MixMappingMonitorMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.beehyv.fortification.mapper.BaseMapper.categoryMapper;

@Component
public class FunctionUtils {
    private final BaseMapper<BatchListResponseDTO, BatchRequestDto, Batch> batchListMapper = BaseMapper.getForListClass(BatchMapper.class);
    private final BaseMapper<MixMappingMonitorResponseDto, MixMappingRequestDto, MixMapping> mixMappingMonitorMapper = BaseMapper.getForListClass(MixMappingMonitorMapper.class);
    private final BaseMapper<LotListResponseDTO, LotRequestDto, Lot> lotListMapper = BaseMapper.getForListClass(LotMapper.class);

    public BatchMonitorResponseDto checkBatchHistory(Batch batch) {
        if (batch == null) return null;
        BatchMonitorResponseDto batchMonitorResponseDto = new BatchMonitorResponseDto();
        batchMonitorResponseDto.setBatchProperties(batchListMapper.toListDTO(batch));
        batchMonitorResponseDto.setCategory(categoryMapper.toDto(batch.getCategory()));
        if (batch.getMixes().isEmpty()) {
            return batchMonitorResponseDto;
        }
        Set<MixMappingMonitorResponseDto> mixMappingMonitorResponseDtoSet = batch.getMixes().stream()
                .map(d -> {
                    MixMappingMonitorResponseDto mixMappingMonitorResponseDto = new MixMappingMonitorResponseDto();
                    BeanUtils.copyProperties(mixMappingMonitorMapper.toListDTO(d), mixMappingMonitorResponseDto);
                    LotMonitorResponseDto lotMonitorResponseDto = new LotMonitorResponseDto();
                    lotMonitorResponseDto.setLotProperties(lotListMapper.toListDTO(d.getSourceLot()));
                    lotMonitorResponseDto.setCategory(categoryMapper.toDto(d.getSourceLot().getCategory()));
                    if (d.getSourceLot().getBatch() != null) {
                        lotMonitorResponseDto.setSourceBatch(checkBatchHistory(d.getSourceLot().getBatch()));
                    } else {
                        if (!d.getSourceLot().getTargetBatchMapping().stream().map(BatchLotMapping::getSourceLot).toList().isEmpty()) {
                            Set<LotHistoryResponseDto> sourceLots = new HashSet<>();
                            lotMonitorResponseDto.setSourceLots(d.getSourceLot().getTargetBatchMapping().stream()
                                    .map(BatchLotMapping::getSourceLot)
                                    .map(l -> checkLotHistory(l))
                                    .collect(Collectors.toSet()));
                        }
                    }
                    mixMappingMonitorResponseDto.setSourceLot(lotMonitorResponseDto);
                    return mixMappingMonitorResponseDto;
                }).collect(Collectors.toSet());
        batchMonitorResponseDto.setMixes(mixMappingMonitorResponseDtoSet);
        return batchMonitorResponseDto;
    }

    public LotHistoryResponseDto checkLotHistory(Lot lot) {
        if (lot == null) return null;
        LotHistoryResponseDto lotHistoryResponseDto = new LotHistoryResponseDto();
        lotHistoryResponseDto.setLotProperties(lotListMapper.toListDTO(lot));
        lotHistoryResponseDto.setCategory(categoryMapper.toDto(lot.getCategory()));
        lotHistoryResponseDto.setSourceBatch(checkBatchHistory(lot.getBatch()));
        if (lot.getTargetBatchMapping().isEmpty()) {
            return lotHistoryResponseDto;
        }
        if (!lot.getTargetBatchMapping().stream().map(BatchLotMapping::getSourceLot).toList().isEmpty()) {
            lotHistoryResponseDto.setSourceLots(lot.getTargetBatchMapping().stream()
                    .map(BatchLotMapping::getSourceLot)
                    .map(this::checkLotHistory)
                    .collect(Collectors.toSet()));
        }
        return lotHistoryResponseDto;
    }

    public ManufacturerCategoryAction  converStringToEnum(Long targetManufactureId, long categoryId, String token){
        String string = IamServiceRestHelper.getActionNameByManaufacturersIdandCategoryId(targetManufactureId, categoryId, token);
        if(string==null){
            string = ManufacturerCategoryAction.CREATION.name();
        }
        ManufacturerCategoryAction manufacturerCategoryAction = ManufacturerCategoryAction.valueOf(string);
        return manufacturerCategoryAction;
    }

}
