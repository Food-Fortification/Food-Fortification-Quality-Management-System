package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.LabCategoryRequestDto;
import org.path.lab.dto.responseDto.LabCategoryResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.LabCategory;
import org.path.lab.manager.LabCategoryManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.LabCategoryService;
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
