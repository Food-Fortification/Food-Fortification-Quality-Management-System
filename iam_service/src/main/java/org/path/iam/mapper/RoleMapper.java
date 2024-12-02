package org.path.iam.mapper;

import org.path.iam.dto.requestDto.RoleRequestDto;
import org.path.iam.dto.responseDto.RoleResponseDto;
import org.path.iam.model.Role;

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
