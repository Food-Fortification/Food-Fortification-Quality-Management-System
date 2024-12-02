package org.path.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto extends BaseRequestDto{
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private Long manufacturerId;
    private Long labId;
    private String userName;
    private String password;
    private List<RoleRequestDto> rolesMap;
    private Boolean isEnabled;
}
