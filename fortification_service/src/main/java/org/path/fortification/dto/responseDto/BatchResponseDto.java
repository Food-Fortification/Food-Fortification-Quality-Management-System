package org.path.fortification.dto.responseDto;

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
public class BatchResponseDto extends BaseResponseDto {
    private Long id;
    private Long manufacturerId;
    private Date dateOfManufacture;
    private Date dateOfExpiry;
    private String licenseId;
    private String batchNo;
    private Double totalQuantity;
    private Double remainingQuantity;
    private String comments;
    private String prefetchedInstructions;
    private String qrcode;
    private String uuid;
    private String batchName;
    private Date lastActionDate;
    @JsonIncludeProperties(value = {"id", "name", "conversionFactorKg"})
    private UOMResponseDto uom;
    @JsonIncludeProperties(value = {"id", "name", "product"})
    private CategoryResponseDto category;
    @JsonIncludeProperties(value = {"id", "name", "label","displayName"})
    private StateResponseDto state;
    private List<StateResponseDto> actions;
    private Set<BatchPropertyResponseDto> batchProperties;
    private Set<BatchDocResponseDto> batchDocs;
    private Set<SizeUnitResponseDto> sizeUnits;
    private Set<LotListResponseDTO> lots;
    private Set<MixMappingResponseDto> mixes;
    private Set<WastageResponseDto> wastes;
}
