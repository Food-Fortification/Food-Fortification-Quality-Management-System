package com.beehyv.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeCategoryRequestDto extends BaseRequestDto{
    private Long id;
    private String category;
    private Set<AttributeRequestDto> attributes;
}
