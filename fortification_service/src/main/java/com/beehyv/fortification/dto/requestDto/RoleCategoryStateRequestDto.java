package com.beehyv.fortification.dto.requestDto;

import com.beehyv.fortification.entity.RoleCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

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
