package org.path.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto extends BaseRequestDto{
    private String roleName;
    private String categoryName;
    private Long categoryId;
    private String roleCategoryType;
}
