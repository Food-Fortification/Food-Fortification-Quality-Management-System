package org.path.iam.dto.responseDto;

import org.path.iam.enums.ActionType;
import org.path.iam.enums.RoleCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUserTokenResponseDto{

    private RoleCategoryType roleCategoryType;
    private ActionType actionType;
    private String registrationToken;

}
