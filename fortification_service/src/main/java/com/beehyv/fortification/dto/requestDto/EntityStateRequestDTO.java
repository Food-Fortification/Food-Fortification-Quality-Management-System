package com.beehyv.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EntityStateRequestDTO extends BaseRequestDto{
    private Long stateId;
    private Long batchId;
    private Long lotId;
    private Long targetManufacturerId;
    private String comments;
    private Boolean isInspectionSample;
    @Deprecated
    private Map actionConfig;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfAction;
    private Long labId;
    private Double acknowledgedQuantity;
}
