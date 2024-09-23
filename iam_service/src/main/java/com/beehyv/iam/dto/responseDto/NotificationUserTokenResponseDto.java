package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.ActionType;
import com.beehyv.iam.enums.RoleCategoryType;
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
