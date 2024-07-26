package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.LabCategoryRequestDto;
import com.beehyv.lab.dto.responseDto.LabCategoryResponseDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabCategory;
import com.beehyv.lab.manager.LabCategoryManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.LabCategoryService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class LabCategoryServiceImpl implements LabCategoryService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final LabCategoryManager manager;


    @Override
    public void create(LabCategoryRequestDto labCategoryRequestDto) {
        manager.create(mapper.mapDtoToEntityLabCategory(labCategoryRequestDto));
    }

    @Override
    public LabCategoryResponseDto getById(Long id) {
        LabCategory labCategory = manager.findById(id);
        if(labCategory != null) {
            return mapper.mapEntityToDtoLabCategory(labCategory);
        } else {
            return null;
        }
    }

    @Override
    public void update(LabCategoryRequestDto labCategoryRequestDto) {
        manager.update(mapper.mapDtoToEntityLabCategory(labCategoryRequestDto));
    }

    @Override
    public void delete(Long id) {
        manager.delete(id);
    }

    @Override
    public ListResponse<LabCategoryResponseDto> getByLabId(Long labId) {
        List<LabCategory> labCategories = manager.findAllByLabId(labId);
        Long count = Long.valueOf(labCategories.size());
        return ListResponse.from(labCategories, mapper::mapEntityToDtoLabCategory, count);
    }

    @Override
    public List<Long> getCategoryIdsByLabId(Long labId) {
        List<LabCategory> labCategories = manager.findAllByLabId(labId);
        return labCategories.stream().map(lc -> lc.getCategoryId()).toList();
    }


}
