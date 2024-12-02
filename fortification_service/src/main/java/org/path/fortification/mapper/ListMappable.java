package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.BaseRequestDto;
import org.path.fortification.dto.responseDto.BaseResponseDto;
import org.path.fortification.entity.Base;

public interface ListMappable<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {
    // Convert Category JPA Entity into CategoryDto
    public ResponseDto toListDto(E entity);
}
