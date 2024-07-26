package com.beehyv.fortification.dto.external;

import com.beehyv.fortification.dto.responseDto.BaseResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExternalResponseDto extends BaseResponseDto {

    private String statusCode;
    private String message;
    private String data;
}
