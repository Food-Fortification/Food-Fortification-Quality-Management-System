package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.entity.RoleCategoryType;

import java.util.List;

public interface RoleCategoryService {
    void createRoleCategory(RoleCategoryRequestDto dto);

    RoleCategoryResponseDto getRoleCategoryById(Long id);

    ListResponse<RoleCategoryResponseDto> getAllRoleCategories(Integer pageNumber, Integer pageSize);

    List<MenuResponseDto> getAllMenuRoleCategories();

    List<MenuLabResponseDto> getMenuForLab();

    void updateRoleCategory(RoleCategoryRequestDto dto);

    void deleteRoleCategory(Long id);

}
