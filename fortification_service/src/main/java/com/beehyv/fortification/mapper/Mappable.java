package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.BaseRequestDto;
import com.beehyv.fortification.entity.Base;
import com.beehyv.fortification.dto.responseDto.BaseResponseDto;

public interface Mappable<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {
    // Convert Category JPA Entity into CategoryDto
    public ResponseDto toDto(E entity);

    // Convert CategoryDto into Category JPA Entity
    public E toEntity(RequestDto dto);
}
