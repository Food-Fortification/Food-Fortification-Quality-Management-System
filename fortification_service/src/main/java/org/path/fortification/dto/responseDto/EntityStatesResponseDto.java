package org.path.fortification.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntityStatesResponseDto {
    private String name;
    private boolean isInventory;
    private boolean canInspect;
    private List<MenuStateResponseDto> states;
}
