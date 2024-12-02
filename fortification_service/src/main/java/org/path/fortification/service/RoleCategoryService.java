package org.path.fortification.service;

import org.path.fortification.dto.requestDto.RoleCategoryRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.RoleCategoryResponseDto;

public interface RoleCategoryService {
    void createRoleCategory(RoleCategoryRequestDto dto);

    RoleCategoryResponseDto getRoleCategoryById(Long id);

    ListResponse<RoleCategoryResponseDto> getAllRoleCategories(Integer pageNumber, Integer pageSize);

    void updateRoleCategory(RoleCategoryRequestDto dto);

    void deleteRoleCategory(Long id);

}
