package org.path.Immudb.dto;

import org.path.Immudb.enums.ActionType;
import org.path.Immudb.enums.RoleCategoryType;
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