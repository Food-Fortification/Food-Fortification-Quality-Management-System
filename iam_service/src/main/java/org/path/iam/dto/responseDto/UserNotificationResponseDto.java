package org.path.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationResponseDto extends UserResponseDto{
    private Set<NotificationUserTokenResponseDto> notificationUserTokens;
}
