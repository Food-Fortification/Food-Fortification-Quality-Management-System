package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTestDetailsResponseDto extends BaseResponseDTO {

    private Long batchId;
    private String labName;
    private String performedBy;
    private String testName;

}
