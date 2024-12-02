package org.path.fortification.service.impl;

import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.manager.UOMManager;
import org.path.fortification.dto.requestDto.UOMRequestDto;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.entity.UOM;
import org.path.fortification.mapper.UOMMapper;
import org.path.fortification.dto.responseDto.UOMResponseDto;
import org.path.fortification.service.UOMService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UOMServiceImpl implements UOMService {
    private final BaseMapper<UOMResponseDto, UOMRequestDto, UOM> mapper = BaseMapper.getForClass(UOMMapper.class);
    private UOMManager manager;

    @Override
    public void createUOM(UOMRequestDto dto) {
        UOM entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public UOMResponseDto getUOMById(Long id) {
        UOM entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<UOMResponseDto> getAllUOMs(Integer pageNumber, Integer pageSize) {
        List<UOM> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateUOM(UOMRequestDto dto) {
        UOM existingUOM = manager.findById(dto.getId());
        if(dto.getName() != null) existingUOM.setName(dto.getName());
        if(dto.getConversionFactorKg() != null) existingUOM.setConversionFactorKg(dto.getConversionFactorKg());
        manager.create(existingUOM);
    }

    @Override
    public void deleteUOM(Long id) {
        manager.delete(id);
    }
}
