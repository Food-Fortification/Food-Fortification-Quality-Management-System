package org.path.broadcast.dto.labResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LabSampleResponseDto extends BaseResponseDto {
    private Long id;
    private Long batchId;
    private Long manufacturerId;
    private String batchNo;
    private String lotNo;
    private Long lotId;
    private Date testDate;
    private Date sampleSentDate;
    private Date receivedDate;
    private String performedBy;
    private Long requestStatusId;
    private Long categoryId;
    private Boolean resultAccepted;
    private Long inspectionId;
    private Boolean isInspectionSample;
    private Boolean isExternalTest;
    private LabResponseDTO lab;
    private SampleStateResponseDTO state;
    private Set<LabTestResponseDTO> labTests;
    private Set<SampleTestDocumentResponseDTO> sampleTestDocuments;
}
