package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.EntityType;
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
