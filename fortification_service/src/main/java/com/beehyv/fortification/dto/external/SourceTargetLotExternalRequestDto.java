package com.beehyv.fortification.dto.external;

import com.beehyv.fortification.dto.requestDto.MixMappingRequestDto;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SourceTargetLotExternalRequestDto {

    private String externalTargetManufacturerId;
    private String destinationTransId;
    private Set<MixMappingRequestDto> mixes;
    private Double acknowledgedQuantity;
}
