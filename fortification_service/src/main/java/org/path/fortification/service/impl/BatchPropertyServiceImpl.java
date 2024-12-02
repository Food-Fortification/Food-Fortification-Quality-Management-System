package org.path.fortification.service.impl;

import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.manager.BatchPropertyManager;
import org.path.fortification.dto.requestDto.BatchPropertyRequestDto;
import org.path.fortification.entity.BatchProperty;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.BatchPropertyMapper;
import org.path.fortification.dto.responseDto.BatchPropertyResponseDto;
import org.path.fortification.service.BatchPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BatchPropertyServiceImpl implements BatchPropertyService {
    private final BaseMapper<BatchPropertyResponseDto, BatchPropertyRequestDto, BatchProperty> mapper = BaseMapper.getForClass(BatchPropertyMapper.class);
    private BatchPropertyManager manager;

    @Override
    public void createBatchProperty(BatchPropertyRequestDto dto) {
        BatchProperty entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public BatchPropertyResponseDto getBatchPropertyById(Long id) {
        BatchProperty entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<BatchPropertyResponseDto> getAllBatchProperties(Integer pageNumber, Integer pageSize) {
        List<BatchProperty> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateBatchProperty(BatchPropertyRequestDto dto) {
        BatchProperty existingBatchProperty = manager.findById(dto.getId());
        if (dto.getName() != null) existingBatchProperty.setName(dto.getName());
        if (dto.getValue() != null) existingBatchProperty.setValue(dto.getValue());
        manager.update(existingBatchProperty);
    }

    @Override
    public void deleteBatchProperty(Long id) {
        manager.delete(id);
    }
}
