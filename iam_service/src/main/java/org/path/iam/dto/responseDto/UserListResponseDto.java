package org.path.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponseDto {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String category;
    private String role;
    private String status;
}
