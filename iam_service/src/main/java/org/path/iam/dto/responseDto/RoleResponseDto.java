package org.path.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto extends BaseResponseDto{
    private Long id;
    private String name;
    private String displayName;
}
