package com.beehyv.Immudb.dto;

import com.beehyv.Immudb.enums.ActionType;
import com.beehyv.Immudb.enums.RoleCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUserTokenResponseDto {

  private RoleCategoryType roleCategoryType;
  private ActionType actionType;
  private String registrationToken;

}