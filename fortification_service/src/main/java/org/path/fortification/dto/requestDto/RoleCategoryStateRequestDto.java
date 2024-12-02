package org.path.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCategoryStateRequestDto extends BaseRequestDto {
    private Long id;
    private Long roleCategoryId;
    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
    private Long stateId;
}
