package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.manager.BatchPropertyManager;
import com.beehyv.fortification.dto.requestDto.BatchPropertyRequestDto;
import com.beehyv.fortification.entity.BatchProperty;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.BatchPropertyMapper;
import com.beehyv.fortification.dto.responseDto.BatchPropertyResponseDto;
import com.beehyv.fortification.service.BatchPropertyService;
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
