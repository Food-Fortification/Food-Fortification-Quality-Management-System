package org.path.iam.dto.requestDto;

import org.path.iam.enums.ComplianceType;
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
