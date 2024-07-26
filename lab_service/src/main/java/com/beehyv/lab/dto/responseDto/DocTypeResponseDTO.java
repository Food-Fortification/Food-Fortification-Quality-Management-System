package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DocTypeResponseDTO extends BaseResponseDTO {
    private Long id;
    private String name;
}
