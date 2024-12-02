package org.path.lab.dto.responseDto;

import org.path.lab.enums.LabSampleResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LabSampleCreateResponseDto {
    private Long id;
    private Long labId;
    private LabSampleResult result;

}
