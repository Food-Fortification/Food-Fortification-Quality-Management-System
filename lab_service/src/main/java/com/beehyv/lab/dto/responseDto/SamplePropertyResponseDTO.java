package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SamplePropertyResponseDTO extends BaseResponseDTO {
    private Long id;
    private Long labSampleId;
    private String keySample;
    private String value;
}
