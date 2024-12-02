package org.path.iam.mapper;

import org.path.iam.dto.requestDto.BaseRequestDto;
import org.path.iam.dto.responseDto.BaseResponseDto;
import org.path.iam.model.Base;

public interface Mappable<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {

    public ResponseDto toDto(E entity);

    public E toEntity(RequestDto dto);
}
