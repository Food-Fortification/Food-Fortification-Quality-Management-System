package com.beehyv.lab.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LabSampleRequestDTO {
    private Long id;
    private Long batchId;
    private Long manufacturerId;
    private String batchNo;
    private String lotNo;
    private Long lotId;
    @PastOrPresent
    @JsonFormat(timezone="IST",pattern = "yyyy-MM-dd")
    private Date testDate;
    @PastOrPresent
    @JsonFormat(timezone="IST",pattern = "yyyy-MM-dd")
    private Date sampleSentDate;
    @PastOrPresent
    @JsonFormat(timezone="IST",pattern = "yyyy-MM-dd")
    private Date receivedDate;
    private String performedBy;
    private Long labId;
    private String requestStatusId;
    private Long categoryId;
    private Set<SamplePropertyRequestDTO> sampleProperties;
    private Set<LabTestRequestDTO> labTests;
    private Set<SampleTestDocumentRequestDTO> sampleTestDocuments;
    private Boolean resultAccepted;
    private Long stateId;
    private String externalManufacturerId;
    private Double percentageCategoryMix;
}
