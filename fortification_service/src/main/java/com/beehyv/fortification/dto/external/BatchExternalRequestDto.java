package com.beehyv.fortification.dto.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BatchExternalRequestDto {

    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfManufacture;
    @PastOrPresent
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date dateOfAcceptance;
    private String destinationTransId;
    private String sourceExternalManufacturerId;
    private String targetExternalManufacturerId;
    private Set<MixMappingExternalRequestDto> mixes;
    @PositiveOrZero(message = "Total Quantity cannot be less than 0")
    private Double totalQuantity;
    @PositiveOrZero(message = "Total Quantity cannot be less than 0")
    private Double acknowledgedQuantity;
    private String comments;
    private String batchName;
    private Set<ExternalLabTest> labTests;
}
