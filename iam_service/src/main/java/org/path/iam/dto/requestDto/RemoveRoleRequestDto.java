package org.path.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RemoveRoleRequestDto extends BaseRequestDto {
    private String userName;
    private Long roleCategoryId;
}
