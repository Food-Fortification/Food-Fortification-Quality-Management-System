package com.beehyv.fortification.dto.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TargetLotsExternalRequestDto {

    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfDispatch;
    private Set<SourceTargetLotExternalRequestDto> sourceTargetLots;
    private String comments;
    private Date dateOfAcceptance;
}
