package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.CategoryRequestDto;
import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.CategoryResponseDto;
import com.beehyv.fortification.entity.Category;
import com.beehyv.fortification.entity.RoleCategory;
import com.beehyv.fortification.entity.RoleCategoryState;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.dto.requestDto.StateRequestDto;
import com.beehyv.fortification.dto.responseDto.RoleCategoryResponseDto;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleCategoryMapper implements Mappable<RoleCategoryResponseDto, RoleCategoryRequestDto, RoleCategory> {
    private final BaseMapper<StateResponseDto, StateRequestDto, State> stateMapper = BaseMapper.getForClass(StateMapper.class);
    private final BaseMapper<CategoryResponseDto, CategoryRequestDto, Category> categoryMapper = BaseMapper.getForClass(CategoryMapper.class);
    // Convert User JPA Entity into RoleCategoryDto
    @Override
    public RoleCategoryResponseDto toDto(RoleCategory entity) {
        RoleCategoryResponseDto dto = new RoleCategoryResponseDto();
        dto.setId(entity.getId());
        dto.setRoleName(entity.getRoleName());
        dto.setRoleCategoryType(entity.getRoleCategoryType());
        if(entity.getCategory() != null) dto.setCategory(categoryMapper.toDto(entity.getCategory()));
        return dto;
    }

    // Convert RoleCategoryDto into RoleCategory JPA Entity
    @Override
    public RoleCategory toEntity(RoleCategoryRequestDto dto) {
        RoleCategory entity = new RoleCategory();
        entity.setId(dto.getId());
        entity.setRoleName(dto.getRoleName());
        if(dto.getCategoryId() != null) {
            entity.setCategory(new Category(dto.getCategoryId()));
        }
        entity.setRoleCategoryType(dto.getRoleCategoryType());
        if(dto.getRoleCategoryStates() != null) {
            entity.setRoleCategoryStates(
                    dto.getRoleCategoryStates().stream().map(d -> {
                        RoleCategoryState rcs = new RoleCategoryState();
                        rcs.setState(new State(d.getStateId()));
                        rcs.setCategory(new Category(d.getCategoryId()));
                        rcs.setRoleCategory(entity);
                        return rcs;
                    }).collect(Collectors.toSet())
            );
        }
        return entity;
    }
}
