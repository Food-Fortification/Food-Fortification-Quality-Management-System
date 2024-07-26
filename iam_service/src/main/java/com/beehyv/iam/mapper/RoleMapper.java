package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.RoleRequestDto;
import com.beehyv.iam.dto.responseDto.RoleResponseDto;
import com.beehyv.iam.model.Role;

public class RoleMapper implements Mappable<RoleResponseDto, RoleRequestDto, Role>{
    @Override
    public RoleResponseDto toDto(Role entity) {
        RoleResponseDto dto = new RoleResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDisplayName(entity.getDisplayName());
        return dto;
    }

    @Override
    public Role toEntity(RoleRequestDto dto) {
        return null;
    }

}
