package com.beehyv.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto extends BaseResponseDto{
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private Long manufacturerId;
    private Long labId;
    @JsonIgnoreProperties({"user"})
    private Set<UserRoleCategoryResponseDto> roleCategory;
    private Date lastLogin;

}
