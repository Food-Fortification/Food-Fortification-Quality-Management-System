package org.path.fortification.dto.requestDto;

import org.path.fortification.entity.RoleCategoryType;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private Set<Long> roleCategoryIds;
    private RoleCategoryType roleCategoryType;
}

