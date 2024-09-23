package com.beehyv.broadcast.dto.fortificationResponseDto;

import com.beehyv.broadcast.dto.commonDtos.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LotListResponseDTO extends BaseResponseDto {
    private Long id;
    private String lotNo;
    private Long targetManufacturerId;
    private Boolean isReceivedAtTarget;
    private Boolean isTargetAcknowledgedReport;
    private Boolean isTargetAccepted;
    private Date dateOfManufacture;
    private Date dateOfExpiry;
    private Date dateOfReceiving;
    private Date dateOfAcceptance;
    private Date dateOfDispatch;

    private Double totalQuantity;
    private Double remainingQuantity;
    private String state;
    private String uuid;
    @JsonIncludeProperties(value = {"uuid", "id", "batchNo", "comments", "prefetchedInstructions", "manufacturerId"})
    private BatchResponseDto batch;
    private String uom;
    private String comments;
    private String manufacturerName;
    private String manufacturerAddress;
    @JsonIncludeProperties(value = {"id", "name"})
    private CategoryResponseDto category;
}
