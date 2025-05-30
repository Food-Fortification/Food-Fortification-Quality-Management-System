package org.path.broadcast.dto.commonDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
    private String description;
}
