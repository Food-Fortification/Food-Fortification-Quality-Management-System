package com.beehyv.iam.dto.responseDto;

import com.beehyv.iam.enums.RoleCategoryType;
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
public class UserCategoryListResponseDto {
  private List<String> categoryNames;
  private LocalDateTime notificationLastSeenTime;
  private RoleCategoryType roleCategoryType;
}
