package com.beehyv.iam.dto.external;

import com.beehyv.iam.dto.requestDto.BaseRequestDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExternalMetaDataRequestDto extends BaseRequestDto {
    private Long id;
    private String name;
    private String value;
}
