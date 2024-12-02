package org.path.iam.dto.responseDto;

import org.path.iam.enums.AttributeScoreType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeResponseDto extends BaseResponseDto{
    private Long id;
    private String name;
    private Boolean isActive;
    private Double weightage;
    private Integer totalScore;
    private Integer defaultScore;
    private AttributeScoreType type;

    @JsonIgnoreProperties(value = {"attributes"})
    private AttributeCategoryResponseDto attributeCategory;
}
