package org.path.fortification.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchPropertyResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
    private String value;
}
