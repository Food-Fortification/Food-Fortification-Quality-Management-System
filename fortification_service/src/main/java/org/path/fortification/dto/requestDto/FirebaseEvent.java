package org.path.fortification.dto.requestDto;

import org.path.fortification.enums.EntityType;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FirebaseEvent {
  private Long id;
  private EntityType entity;
  private Long targetManufacturerId;
  private Long categoryId;
  private String categoryName;
  private Long manufacturerId;
  private String labId;
  private Date dateOfAction;
  private LocalDateTime notificationDate;
  private String entityNo;
  private Boolean isIndependentBatch;
  private String currentStateName;
  private String previousStateName;
  private String currentStateDisplayName;
  private String previousStateDisplayName;
  private Long labSampleId;
}
