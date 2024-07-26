package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.BaseRequestDto;
import com.beehyv.iam.dto.responseDto.BaseResponseDto;
import com.beehyv.iam.model.Base;

public interface Mappable<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {

    public ResponseDto toDto(E entity);

    public E toEntity(RequestDto dto);
}
