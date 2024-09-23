package com.beehyv.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDocRequestDto extends BaseRequestDto{
    private Long id;

    private Long categoryId;


    private Long docTypeId;

    private Boolean isMandatory;
    private Boolean isEnabled;
}

