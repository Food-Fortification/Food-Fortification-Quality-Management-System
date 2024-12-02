package org.path.broadcast.dto.fortificationResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import org.path.broadcast.enums.ActionType;
import org.path.broadcast.enums.StateType;
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
