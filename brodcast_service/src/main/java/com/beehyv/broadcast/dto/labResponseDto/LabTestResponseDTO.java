package com.beehyv.broadcast.dto.labResponseDto;

import com.beehyv.broadcast.dto.commonDtos.BaseResponseDto;
import com.beehyv.broadcast.enums.LabSampleResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTestResponseDTO extends BaseResponseDto {
    private Long id;
    private String testName;
    private String defaultPresent;
    private String value;
    private Date testDate;
    private String performedBy;
    private Long labSampleId;
    private Long requestStateId;
    private String uom;
    private String testMethodFollowed;
    private String referenceMethod;
    private Double minValue;
    private Double maxValue;
    private LabSampleResult testResult;
    private Boolean isMandatory;

}
