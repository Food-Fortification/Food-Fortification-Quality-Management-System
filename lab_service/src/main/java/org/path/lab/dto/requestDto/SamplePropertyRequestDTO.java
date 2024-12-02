package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SamplePropertyRequestDTO {
    private Long id;
    private Long labSampleId;
    private String keySample;
    private String value;
}
