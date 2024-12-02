package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerCategoryResponseDto extends BaseResponseDto{
    private Long id;
    private Long categoryId;
    private Boolean canSkipRawMaterials;
    private Boolean isEnabled;
    @JsonIgnoreProperties(value = {"manufacturersDocs","village"})
    private ManufacturerResponseDto manufacturerDto;
}
