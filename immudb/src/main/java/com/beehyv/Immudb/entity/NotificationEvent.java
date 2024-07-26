package com.beehyv.Immudb.entity;

import com.beehyv.Immudb.enums.EntityType;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private EntityType entityType;
  private Long entityId;
  private String entityNo;
  private Long manufacturerId;
  private Long targetManufacturerId;
  private Long categoryId;
  private String categoryName;
  private Long labId;
  @Temporal(TemporalType.DATE)
  private Date dateOfAction;
  private LocalDateTime notificationDate;
  @Column(columnDefinition = "TEXT")
  private String notificationTitle;
  private Boolean isIndependentBatch;
  private String currentStateName;
  private String previousStateName;
  private Boolean isTargetManufacturer;
  private String currentStateDisplayName;
  private String previousStateDisplayName;
  private Long labSampleId;
}
