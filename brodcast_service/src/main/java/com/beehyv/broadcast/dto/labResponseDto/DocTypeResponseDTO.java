package com.beehyv.broadcast.dto.labResponseDto;

import com.beehyv.broadcast.dto.commonDtos.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DocTypeResponseDTO extends BaseResponseDto {
    private Long id;
    private String name;
}
