package org.path.iam.mapper;

import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.requestDto.UserRoleCategoryRequestDto;
import org.path.iam.dto.responseDto.RoleResponseDto;
import org.path.iam.dto.responseDto.UserResponseDto;
import org.path.iam.dto.responseDto.UserRoleCategoryResponseDto;
import org.path.iam.model.User;
import org.path.iam.model.UserRoleCategory;

public class UserRoleCategoryMapper implements Mappable<UserRoleCategoryResponseDto, UserRoleCategoryRequestDto, UserRoleCategory>{
    private static final BaseMapper<UserResponseDto, UserRequestDto, User> userMapper = BaseMapper.getForClass(UserMapper.class);
    @Override
    public UserRoleCategoryResponseDto toDto(UserRoleCategory entity) {
        UserRoleCategoryResponseDto dto = new UserRoleCategoryResponseDto();
        dto.setId(entity.getId());
        dto.setUser(userMapper.toDto(entity.getUser()));
        dto.setCategory(entity.getCategory());
        dto.setRole(new RoleResponseDto(entity.getRole().getId(),entity.getRole().getName(),entity.getRole().getDisplayName()));
        return dto;
    }

    @Override
    public UserRoleCategory toEntity(UserRoleCategoryRequestDto dto) {
        UserRoleCategory entity = new UserRoleCategory();
        if (dto.getId()!=null)entity.setId(dto.getId());
        entity.setUser(new User(dto.getUserId()));
        entity.setCategory(dto.getCategoryName());
        return entity;
    }
}
