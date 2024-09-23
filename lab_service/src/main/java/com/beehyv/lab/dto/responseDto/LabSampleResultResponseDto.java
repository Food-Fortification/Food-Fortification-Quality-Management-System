package com.beehyv.lab.dto.responseDto;

import com.beehyv.lab.enums.LabSampleResult;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabSampleResultResponseDto extends BaseResponseDTO{
    private LabSampleResult labSampleResult;
    private Boolean isExternalTest;
    private Long labId;
}
