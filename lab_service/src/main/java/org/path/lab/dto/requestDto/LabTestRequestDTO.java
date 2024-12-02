package org.path.lab.dto.requestDto;

import org.path.lab.enums.LabSampleResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTestRequestDTO {
    private Long id;
    private Long labTestReferenceMethodId;
    private String testName;
    private String defaultPresent;
    private String value;
    @PastOrPresent
    @JsonFormat(timezone="IST",pattern = "yyyy-MM-dd")
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
