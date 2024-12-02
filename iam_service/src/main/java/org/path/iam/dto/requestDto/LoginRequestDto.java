package org.path.iam.dto.requestDto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDto {
    private String userName;
    private String password;
    private Long userId;
}
