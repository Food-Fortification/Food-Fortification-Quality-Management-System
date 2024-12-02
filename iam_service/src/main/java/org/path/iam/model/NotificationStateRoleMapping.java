package org.path.iam.model;

import org.path.iam.enums.RoleCategoryType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationStateRoleMapping extends Base{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String categoryName;
  @Enumerated(value = EnumType.STRING)
  private RoleCategoryType roleCategoryType;
  @ManyToOne
  private Role role;
  @ManyToOne
  private NotificationState notificationState;

}
