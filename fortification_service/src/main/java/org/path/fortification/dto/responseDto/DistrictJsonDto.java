package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictJsonDto {
    private String externalDistrictId;
    private String districtId;
}
