package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.MixMappingCommentsRequestDto;
import org.path.fortification.dto.requestDto.MixMappingRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.MixMappingResponseDto;
import org.path.fortification.entity.*;
import org.path.fortification.manager.*;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.MixMappingMapper;
import org.path.fortification.service.MixMappingService;
import org.path.parent.exceptions.CustomException;
import org.path.parent.exceptions.ResourceNotFoundException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class MixMappingServiceImpl implements MixMappingService {
    private final BaseMapper<MixMappingResponseDto, MixMappingRequestDto, MixMapping> mapper = BaseMapper.getForClass(MixMappingMapper.class);
    private MixMappingManager manager;
    private BatchManager batchManager;
    private LotManager lotManager;
    private UOMManager uomManager;
    private CategoryManager categoryManager;
    @Autowired
    private KeycloakInfo keycloakInfo;

    @Override
    public void createMixMapping(Long batchId, MixMappingRequestDto dto) {
        MixMapping entity = mapper.toEntity(dto);
        entity.setTargetBatch(new Batch(batchId));
        manager.create(entity);
    }

    @Override
    public MixMappingResponseDto getMixMappingById(Long id) {
        MixMapping entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<MixMappingResponseDto> getAllMixMappingsByTargetBatch(Long targetBatchId, Integer pageNumber, Integer pageSize) {
        List<MixMapping> entities = manager.findAllByTargetBatchId(targetBatchId, pageNumber, pageSize);
        Long count = manager.getCountByTargetBatchId(entities.size(), targetBatchId, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public ListResponse<MixMappingResponseDto> getAllMixMappingsBySourceLot(Long sourceLotId, Integer pageNumber, Integer pageSize) {
        List<MixMapping> entities = manager.findAllBySourceLotId(sourceLotId, pageNumber, pageSize);
        Long count = manager.getCountBySourceLotId(entities.size(), sourceLotId, pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateMixMapping(Long batchId, MixMappingRequestDto dto) {
        MixMapping existingMixMapping = manager.findById(dto.getId());
        if (!Objects.equals(existingMixMapping.getTargetBatch().getId(), batchId)) {
            throw new BadRequestException("Invalid batch id");
        }
        manager.update(existingMixMapping);
    }

    @Override
    public void deleteMixMapping(Long id) {
        manager.findById(id);
        manager.delete(id);
    }

    @Override
    public void updateBatchMixes(MixMappingCommentsRequestDto mixesInformation, Long batchId) {
        List<MixMappingRequestDto> mixes = mixesInformation.getMixMappingRequestDtoList();
        if (mixes == null || mixes.isEmpty())
            throw new CustomException("Mixes for a batch cannot be null", HttpStatus.BAD_REQUEST);
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        Batch batch = batchManager.findById(batchId);
        if (mixesInformation.getComments() != null)
            batch.setComments(mixesInformation.getComments());
        List<MixMapping> existingMixes = manager.findAllByTargetBatchId(batchId, null, null);
        existingMixes.forEach(m -> {
            Lot sourceLot = m.getSourceLot();
            double quantity = sourceLot.getRemainingQuantity() * sourceLot.getUom().getConversionFactorKg() + m.getQuantityUsed() * m.getUom().getConversionFactorKg();
            sourceLot.setRemainingQuantity(quantity / sourceLot.getUom().getConversionFactorKg());
            lotManager.update(sourceLot);
        });
        List<Lot> sourceLots = lotManager.getAllByIds(mixes.stream().map(MixMappingRequestDto::getSourceLotId).collect(Collectors.toList()));
        if (!categoryManager.isCategorySuperAdmin(batch.getCategory().getId(), RoleCategoryType.MODULE)) {
            if (sourceLots.stream().filter(d -> !Objects.equals(d.getTargetManufacturerId(), manufacturerId)).toList().size() > 0) {
                throw new ValidationException("All lots should belongs to your organization");
            }
        }
        if (sourceLots.stream().filter(d -> !Objects.equals(d.getState().getName(), "approved")).toList().size() > 0) {
            throw new ValidationException("All lots should be approved");
        }
        Map<Long, Lot> lotsMap = sourceLots.stream().collect(Collectors.toMap(Lot::getId, d -> d));
        mixes.forEach(mixMapping -> {
            Lot lot = lotsMap.get(mixMapping.getSourceLotId());
            UOM uom = uomManager.findById(mixMapping.getUomId());
            if (lot == null) throw new ResourceNotFoundException("Mix's Lot", "id", mixMapping.getSourceLotId());
            double remQuantity = lot.getRemainingQuantity() * lot.getUom().getConversionFactorKg()
                    - mixMapping.getQuantityUsed() * uom.getConversionFactorKg();
            if (remQuantity < 0) {
                throw new ValidationException("Lot with id " + lot.getLotNo() + " doesn't not contain enough quantity");
            }
            lot.setRemainingQuantity(remQuantity / lot.getUom().getConversionFactorKg());
            lotManager.save(lot);
        });
        Set<MixMapping> mixMappings = mixes.stream().map(mapper::toEntity).peek(m->m.setTargetBatch(new Batch(batchId))).collect(Collectors.toSet());
        batch.getMixes().removeIf(m->m.getId()!=null);
        batch.getMixes().addAll(mixMappings);
        batchManager.update(batch);
    }
}
