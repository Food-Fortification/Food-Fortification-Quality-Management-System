package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.BaseRequestDto;
import org.path.fortification.entity.Base;
import org.path.fortification.dto.responseDto.BaseResponseDto;

public interface Mappable<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {
    // Convert Category JPA Entity into CategoryDto
    public ResponseDto toDto(E entity);

    // Convert CategoryDto into Category JPA Entity
    public E toEntity(RequestDto dto);
}
