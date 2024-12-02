package org.path.fortification.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuLabResponseDto extends MenuBaseDTO {
    private List<MenuStateResponseDto> entityStates;
    private Integer sequence;
}
