package com.beehyv.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto extends BaseRequestDto {

    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String description;
}
