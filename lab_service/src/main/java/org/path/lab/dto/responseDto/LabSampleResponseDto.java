package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LabSampleResponseDto extends BaseResponseDTO{
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
    private Set<SamplePropertyResponseDTO> sampleProperties;
    private Set<LabTestResponseDTO> labTests;
    private Set<SampleTestDocumentResponseDTO> sampleTestDocuments;
}
