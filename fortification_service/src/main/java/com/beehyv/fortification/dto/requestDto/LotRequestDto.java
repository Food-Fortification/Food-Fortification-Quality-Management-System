package com.beehyv.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LotRequestDto extends BaseRequestDto {
    private Long id;
    private String lotNo;
    private Long targetManufacturerId;
    private String externalTargetManufacturerId;
    private Long stateId;
    @NotNull(message = "Batch id cannot be null")
    private Long batchId;
    private Long uomId;
    private Boolean isReceivedAtTarget;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfReceiving;
    private Boolean isTargetAcknowledgedReport;
    private Boolean isTargetAccepted;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfAcceptance;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfDispatch;
    private Set<SizeUnitRequestDto> sizeUnits;
    @PositiveOrZero(message = "Quantity must be positive")
    private Double totalQuantity;
    @PositiveOrZero(message = "Quantity must be positive")
    private Double remainingQuantity;
    private String comments;
    private Set<WastageRequestDto> wastes;
    private TransportQuantityDetailsRequestDto transportQuantityDetailsRequestDto;
    private String commodityId;
    private Set<LotPropertyRequestDto> lotProperties;

    private Long targetCategoryId;
}
