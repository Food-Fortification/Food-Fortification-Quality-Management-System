package org.path.fortification.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuResponseDto extends MenuBaseDTO {
    private List<EntityStatesResponseDto> entityStates;
    private Integer sequence;
    private boolean independentBatch;
}
