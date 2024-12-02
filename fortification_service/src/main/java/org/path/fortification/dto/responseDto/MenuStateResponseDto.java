package org.path.fortification.dto.responseDto;

import org.path.fortification.entity.StateType;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuStateResponseDto extends BaseResponseDto {
    private List<Long> ids;
    private StateType type;
    private Integer sequence;
    private String name;
    private String displayName;
    private String actionName;
}
