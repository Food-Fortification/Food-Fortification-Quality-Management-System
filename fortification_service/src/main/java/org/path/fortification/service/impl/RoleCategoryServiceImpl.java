package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.RoleCategoryRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.RoleCategoryResponseDto;
import org.path.fortification.entity.RoleCategory;
import org.path.fortification.manager.RoleCategoryManager;
import org.path.fortification.manager.SourceCategoryMappingManager;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.RoleCategoryMapper;
import org.path.fortification.service.RoleCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class RoleCategoryServiceImpl implements RoleCategoryService {
    private final BaseMapper<RoleCategoryResponseDto, RoleCategoryRequestDto, RoleCategory> mapper = BaseMapper.getForClass(RoleCategoryMapper.class);
    @Autowired
    private RoleCategoryManager manager;

    @Autowired
    SourceCategoryMappingManager sourceCategoryMappingManager;

    @Override
    public void createRoleCategory(RoleCategoryRequestDto dto) {
        RoleCategory entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public RoleCategoryResponseDto getRoleCategoryById(Long id) {
        RoleCategory entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<RoleCategoryResponseDto> getAllRoleCategories(Integer pageNumber, Integer pageSize) {
        List<RoleCategory> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateRoleCategory(RoleCategoryRequestDto dto) {
        RoleCategory existingRoleCategory = manager.findById(dto.getId());
        RoleCategory entity = mapper.toEntity(dto);
        entity.setUuid(existingRoleCategory.getUuid());
        manager.update(entity);
    }

    @Override
    public void deleteRoleCategory(Long id) {
        manager.findById(id);
        manager.delete(id);
    }
}
