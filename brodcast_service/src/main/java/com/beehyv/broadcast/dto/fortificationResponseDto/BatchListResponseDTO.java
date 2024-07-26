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
public class BatchListResponseDTO extends BaseResponseDto {
    private Long id;
    private String uuid;
    private String batchNo;
    private String name = "";
    @JsonIncludeProperties(value = {"id", "name", "isIndependentBatch"})
    private CategoryResponseDto category;
    private Date dateOfManufacture;
    private Date dateOfExpiry;
    private Double totalQuantity;
    private Double remainingQuantity;
    private String uom;
    private String state;
    private String comments;
    private String prefetchedInstructions;
    private Long manufacturerId;
}
