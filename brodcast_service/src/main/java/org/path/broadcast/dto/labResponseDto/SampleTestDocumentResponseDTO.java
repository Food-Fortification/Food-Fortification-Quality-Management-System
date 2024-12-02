package org.path.broadcast.dto.labResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SampleTestDocumentResponseDTO extends BaseResponseDto {
    private Long id;
    private CategoryDocumentRequirementResponseDTO categoryDoc;
    private Long labSampleId;
    private String name;
    private String path;
}
