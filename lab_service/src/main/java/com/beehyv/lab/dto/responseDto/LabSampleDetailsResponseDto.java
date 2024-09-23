package com.beehyv.lab.dto.responseDto;

import com.beehyv.lab.entity.SampleState;
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
public class LabSampleDetailsResponseDto extends BaseResponseDTO{
    private Long id;
    private Long batchId;
    private String batchNo;
    private String lotNo;
    private Long lotId;
    private Date testDate;
    private Date sampleSentDate;
    private Date receivedDate;
    private Long inspectionId;
    private Boolean isInspectionSample;
    private Boolean isExternalTest;
    private LabResponseDTO lab;
    private SampleState state;
    private Set<SamplePropertyResponseDTO> sampleProperties;
    private Set<LabTestResponseDTO> labTests;
    private Set<SampleTestDocumentResponseDTO> sampleTestDocuments;
}
