package com.beehyv.fortification.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LotResponseDto extends BaseResponseDto {
    private Long id;
    private String lotNo;
    private String qrcode;
    private Long manufacturerId;
    private Long targetManufacturerId;
    private Date dateOfManufacture;
    private Date dateOfExpiry;
    private Boolean isReceivedAtTarget;
    private Date dateOfReceiving;
    private Boolean isTargetAcknowledgedReport;
    private Boolean isTargetAccepted;
    private Date dateOfAcceptance;
    private Date dateOfDispatch;
    private Double totalQuantity;
    private Double remainingQuantity;
    private StateResponseDto state;
    private Date lastActionDate;
    private String manufacturerLotNumber;
    private TransportQuantityDetailsResponseDto transportQuantityDetailsResponseDto;
    @JsonIncludeProperties(value = {"id", "name", "product"})
    private CategoryResponseDto category;

    private List<MixMappingResponseDto> usage;
    @JsonIgnoreProperties(value = {"category", "actions", "lots", "wastes", "remainingQuantity"})
    private BatchResponseDto batch;
    @JsonIncludeProperties(value = {"uuid", "id", "name", "conversionFactorKg"})
    private UOMResponseDto uom;
    private Set<SizeUnitResponseDto> sizeUnits;
    private String comments;
    private Set<WastageResponseDto> wastes;
    private String prefetchedInstructions;
    @JsonIgnoreProperties(value = {"sourceLots", "targetLots"})
    private Set<LotResponseDto> sourceLots;

    @JsonIgnoreProperties(value = {"sourceLots", "targetLots"})
    private Set<LotResponseDto> targetLots;

    private Set<LotPropertyResponseDto> lotProperties;
}
