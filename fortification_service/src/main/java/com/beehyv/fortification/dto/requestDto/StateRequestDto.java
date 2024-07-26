package com.beehyv.fortification.dto.requestDto;

import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.ActionType;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StateRequestDto extends BaseRequestDto {
    private Long id;
    private StateType type;
    private ActionType actionType;
    private Integer sequence;
    private String name;
    private String displayName;
    private String actionName;
}
