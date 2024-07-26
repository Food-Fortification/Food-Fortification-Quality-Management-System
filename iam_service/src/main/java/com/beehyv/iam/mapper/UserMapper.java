package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.ManufacturerRequestDto;
import com.beehyv.iam.dto.requestDto.UserRequestDto;
import com.beehyv.iam.dto.responseDto.ManufacturerResponseDto;
import com.beehyv.iam.dto.responseDto.UserResponseDto;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.model.Status;
import com.beehyv.iam.model.User;
import org.springframework.beans.BeanUtils;


public class UserMapper implements Mappable<UserResponseDto, UserRequestDto, User>{
    private final BaseMapper<ManufacturerResponseDto, ManufacturerRequestDto, Manufacturer> manufacturerMapper = BaseMapper.getForClass(ManufacturerMapper.class);

    @Override
    public UserResponseDto toDto(User entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setUserName(entity.getUserName());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setLastLogin(entity.getLastLogin());
        if (entity.getManufacturer()!=null)dto.setManufacturerId(entity.getManufacturer().getId());
        if (entity.getLabUsers()!=null)dto.setLabId(entity.getLabUsers().getLabId());
        return dto;
    }

    @Override
    public User toEntity(UserRequestDto dto) {
        User entity = new User();
        if (dto.getId()!=null)entity.setId(dto.getId());
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
       if(dto.getManufacturerId()!=null && dto.getManufacturerId()!=0) entity.setManufacturer(new Manufacturer(dto.getManufacturerId()));
        //TODO:check the lastlogin field
        return entity;
    }
    public static User toEntity(UserRequestDto dto, User existingUser){
        User user = new User();
        BeanUtils.copyProperties(existingUser, user);
        if(dto.getFirstName()!=null) user.setFirstName(dto.getFirstName());
        if (dto.getUserName()!=null)user.setUserName(dto.getUserName());
        if (dto.getLastName()!=null)user.setLastName(dto.getLastName());
        if (dto.getEmail()!=null)user.setEmail(dto.getEmail());
        if (dto.getStatusId()!=null) user.setStatus(new Status(dto.getStatusId()));
        return user;
    }
}
