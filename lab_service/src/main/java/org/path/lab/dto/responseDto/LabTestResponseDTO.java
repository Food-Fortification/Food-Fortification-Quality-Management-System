package org.path.lab.dto.responseDto;

import org.path.lab.enums.LabSampleResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTestResponseDTO extends BaseResponseDTO {
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
