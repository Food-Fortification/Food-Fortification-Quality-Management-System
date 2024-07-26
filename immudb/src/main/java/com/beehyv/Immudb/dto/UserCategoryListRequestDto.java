package com.beehyv.Immudb.dto;

import com.beehyv.Immudb.enums.RoleCategoryType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryListRequestDto {
  private List<String> categoryNames;
  private LocalDateTime notificationLastSeenTime;
  private RoleCategoryType roleCategoryType;
}

