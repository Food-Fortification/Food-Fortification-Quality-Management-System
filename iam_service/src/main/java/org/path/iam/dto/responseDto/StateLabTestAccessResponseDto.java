package org.path.iam.dto.responseDto;

import org.path.iam.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateLabTestAccessResponseDto extends BaseResponseDto{
    private StateResponseDto stateResponseDto;
    private EntityType entityType;
    private Long categoryId;
    private Boolean labSelectionAllowed;
    private Boolean blockWorkflowForTest;

}
