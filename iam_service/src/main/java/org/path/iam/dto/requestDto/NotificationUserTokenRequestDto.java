package org.path.iam.dto.requestDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUserTokenRequestDto {
    String userName;
    String registrationToken;
}
