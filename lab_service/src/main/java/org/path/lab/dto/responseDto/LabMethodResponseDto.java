package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabMethodResponseDto {
    private LabTestTypeResponseDTO labTestTypeResponses;
    private List<LabTestReferenceMethodResponseDTO> labTestReferenceMethodResponses;
}
