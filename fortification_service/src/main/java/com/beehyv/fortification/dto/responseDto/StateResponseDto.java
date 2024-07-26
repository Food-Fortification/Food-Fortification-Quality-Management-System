package com.beehyv.fortification.dto.responseDto;

import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.ActionType;
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
