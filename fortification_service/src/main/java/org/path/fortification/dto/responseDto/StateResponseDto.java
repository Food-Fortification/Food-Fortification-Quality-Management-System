package org.path.fortification.dto.responseDto;

import org.path.fortification.entity.StateType;
import org.path.fortification.enums.ActionType;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StateResponseDto extends BaseResponseDto {
    private Long id;
    private StateType type;
    private ActionType actionType;
    private Integer sequence;
    private String name;
    private String displayName;
    private String actionName;
}
