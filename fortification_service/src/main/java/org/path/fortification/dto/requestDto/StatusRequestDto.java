package org.path.fortification.dto.requestDto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequestDto extends BaseRequestDto {
    private Long id;
    private String name;
    private String description;
}
