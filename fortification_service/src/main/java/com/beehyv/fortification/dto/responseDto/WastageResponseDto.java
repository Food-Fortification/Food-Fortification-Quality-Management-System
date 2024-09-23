package com.beehyv.fortification.dto.responseDto;

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
public class WastageResponseDto extends BaseResponseDto {
    private Long id;
    private Double wastageQuantity;
    private Date reportedDate;
    private String comments;
    @JsonIncludeProperties(value = {"uuid", "id", "name", "conversionFactorKg"})
    private UOMResponseDto uom;
}
