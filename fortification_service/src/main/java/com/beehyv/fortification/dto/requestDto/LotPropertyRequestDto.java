package com.beehyv.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LotPropertyRequestDto extends BaseRequestDto {
    private Long id;
    @NotNull(message = "Lot id cannot be null")
    private Long lotId;
    private String name;
    private String value;
}
