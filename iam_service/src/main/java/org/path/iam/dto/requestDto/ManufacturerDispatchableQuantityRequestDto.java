package org.path.iam.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDispatchableQuantityRequestDto extends BaseRequestDto{
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date toDate;
    private List<Long> manufacturerIds;
    private List<String> districtGeoIds;
    private Long sourceManufacturerId;
    private Long categoryId;
    private String groupBy;
}
