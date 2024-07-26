package com.beehyv.broadcast.dto.fortificationResponseDto;

import com.beehyv.broadcast.dto.commonDtos.BaseResponseDto;
import com.beehyv.broadcast.enums.ActionType;
import com.beehyv.broadcast.enums.StateType;
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
