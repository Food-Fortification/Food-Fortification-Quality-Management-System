package org.path.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerPropertyRequestDto extends BaseRequestDto {
    private Long id;
    @NotNull(message = "Manufacturer id cannot be null")
    private Long manufacturerId;
    private String name;
    private String value;
}
