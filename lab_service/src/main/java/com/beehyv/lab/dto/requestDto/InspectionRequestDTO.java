package com.beehyv.lab.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionRequestDTO {
  private Long id;
  private String requestedBy;
  private boolean isBlocking;
  private String comments;
  private LabSampleRequestDTO labSample;
  private Boolean externalTest;
}
