package org.path.lab.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionResponseDTO {
  private Long id;
  private String requestedBy;
  private boolean isBlocking;
  private String comments;
  private LabSampleResponseDto labSample;

}
