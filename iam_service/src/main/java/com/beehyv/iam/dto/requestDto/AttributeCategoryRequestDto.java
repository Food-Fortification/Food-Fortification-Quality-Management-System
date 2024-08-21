package com.beehyv.iam.dto.requestDto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttributeCategoryRequestDto extends BaseRequestDto{
    private Long id;
    private String category;
    private Set<AttributeRequestDto> attributes;
}
