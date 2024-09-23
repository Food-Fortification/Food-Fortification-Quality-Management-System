package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.BaseRequestDto;
import com.beehyv.fortification.dto.responseDto.BaseResponseDto;
import com.beehyv.fortification.entity.Base;

public interface ListMappable<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {
    // Convert Category JPA Entity into CategoryDto
    public ResponseDto toListDto(E entity);
}
