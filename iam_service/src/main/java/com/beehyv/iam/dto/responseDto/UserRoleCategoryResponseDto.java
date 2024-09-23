package com.beehyv.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleCategoryResponseDto extends BaseResponseDto{
    private Long Id;
    @JsonIgnoreProperties({"manufacturerDocs","roles"})
    private UserResponseDto user;
    @JsonIgnoreProperties({"displayName"})
    private RoleResponseDto role;
    private String category;
}
