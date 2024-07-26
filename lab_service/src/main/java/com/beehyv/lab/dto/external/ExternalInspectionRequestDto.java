package com.beehyv.lab.dto.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExternalInspectionRequestDto {
    private String lotNo;
    @PastOrPresent
    @JsonFormat(timezone="IST",pattern = "yyyy-MM-dd")
    private Date sampleSentDate;
    private Double labSampleQuantity;
}
