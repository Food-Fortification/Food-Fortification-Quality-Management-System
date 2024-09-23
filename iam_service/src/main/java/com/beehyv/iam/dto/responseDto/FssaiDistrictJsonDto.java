package com.beehyv.iam.dto.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FssaiDistrictJsonDto {
    private String fssaiDistrictName;
    private String districtName;
    private String geoId;
}
