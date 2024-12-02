package org.path.lab.dto.responseDto;

import org.path.lab.enums.LabSampleResult;
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
