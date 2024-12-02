package org.path.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeCategoryResponseDto extends BaseResponseDto{
    private Long id;
    private String category;

    @JsonIgnoreProperties(value = {"attributeCategory"})
    private Set<AttributeResponseDto> attributes;
}
