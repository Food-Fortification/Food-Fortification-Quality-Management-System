package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ManufacturerEmpanelResponseDto extends BaseResponseDto{
    private Long id;

    private Long manufacturerId;
    private Long categoryId;
    private String stateGeoId;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date toDate;
}
