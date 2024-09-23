package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.*;
import com.beehyv.fortification.manager.RoleCategoryManager;
import com.beehyv.fortification.manager.SourceCategoryMappingManager;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.RoleCategoryMapper;
import com.beehyv.fortification.service.RoleCategoryService;
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
