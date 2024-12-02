package org.path.broadcast.dto.fortificationResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UOMResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
    private Long conversionFactorKg;
}
