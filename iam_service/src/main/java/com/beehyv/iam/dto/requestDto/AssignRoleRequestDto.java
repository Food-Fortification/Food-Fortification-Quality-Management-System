package com.beehyv.iam.dto.requestDto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AssignRoleRequestDto extends BaseRequestDto{
    String userName;
    List<RoleRequestDto> roles;
}
