package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateLabTestAccessRequestDto extends BaseRequestDto{

    @NotNull(message = "State id cannot be null")
    private Long stateId;
    private EntityType entityType;

    private Long categoryId;

    private Boolean labSelectionAllowed;

    private Boolean blockWorkflowForTest;

}
