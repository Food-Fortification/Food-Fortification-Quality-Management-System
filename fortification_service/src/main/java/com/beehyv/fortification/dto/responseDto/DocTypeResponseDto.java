package com.beehyv.fortification.dto.responseDto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocTypeResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
}
