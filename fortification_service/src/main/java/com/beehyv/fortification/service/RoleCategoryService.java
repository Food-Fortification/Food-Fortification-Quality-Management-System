package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.RoleCategoryRequestDto;
import com.beehyv.fortification.dto.responseDto.*;

public interface RoleCategoryService {
    void createRoleCategory(RoleCategoryRequestDto dto);

    RoleCategoryResponseDto getRoleCategoryById(Long id);

    ListResponse<RoleCategoryResponseDto> getAllRoleCategories(Integer pageNumber, Integer pageSize);

    void updateRoleCategory(RoleCategoryRequestDto dto);

    void deleteRoleCategory(Long id);

}
