package com.beehyv.Immudb.dto;

import com.beehyv.Immudb.enums.EntityType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {

  private EntityType entityType;
  private Long entityId;
  private String entityNo;
  private Long categoryId;
  private String categoryName;
  private String notificationTitle;
  private String currentStateDisplayName;
  private Boolean isIndependentBatch;
  private String previousStateDisplayName;
  private LocalDateTime notificationDate;
  private Long labSampleId;
}
