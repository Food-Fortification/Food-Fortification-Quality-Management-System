package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.enums.ComplianceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerAttributeScoreRequestDto extends BaseRequestDto{
    private Long id;
    private Long attributeId;
    private ComplianceType compliance;
    private String value;
    private Long attributeCategoryScoreId;
}
