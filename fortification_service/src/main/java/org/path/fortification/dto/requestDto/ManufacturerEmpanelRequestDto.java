package org.path.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerEmpanelRequestDto extends BaseRequestDto{

    private String stateGeoId;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date toDate;
    private Long sourceCategoryId;
    private Long targetCategoryId;
}
