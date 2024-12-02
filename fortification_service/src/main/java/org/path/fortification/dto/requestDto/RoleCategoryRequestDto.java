package org.path.fortification.dto.requestDto;

import org.path.fortification.entity.RoleCategoryType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCategoryRequestDto extends BaseRequestDto {
    private Long id;
    private String roleName;
    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
    private RoleCategoryType roleCategoryType;
    private Set<@NotNull RoleCategoryStateRequestDto> roleCategoryStates;
}
