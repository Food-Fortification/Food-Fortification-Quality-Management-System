package org.path.iam.dto.requestDto;

import org.path.iam.enums.ManufacturerCategoryAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerCategoryRequestDto extends BaseRequestDto{
    private Long id;
    private Long categoryId;
    private Boolean canSkipRawMaterials;
    private Long manufacturerId;
    private Boolean isEnabled;
    private ManufacturerCategoryAction action;
}
