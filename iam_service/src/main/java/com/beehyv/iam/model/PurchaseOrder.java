package com.beehyv.iam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE manufacturer SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class PurchaseOrder extends Base{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull(message = "Please provide the source manufacturer id")
  private Long sourceManufacturerId;
  @NotNull(message = "Please provide the target manufacturer id")
  private Long targetManufacturerId;
  @NotNull(message = "Please provide the purchase order id")
  private String purchaseOrderId;
  @NotNull(message = "Please provide the maximum units to be dispatched")
  private Double maxDispatchableQuantity;
  @NotNull(message = "Please provide the units dispatched")
  private Double dispatchedQuantity;
  @NotNull(message = "Please provide the source manufacturer id")
  private String externalSourceManufacturerId;
  @NotNull(message = "Please provide the target manufacturer id")
  private String externalTargetManufacturerId;
  private Date allotedMonth;
  private String dmOrderNo;
  private Date dmOrderDate;
  private Date dmDelieverDate;
  private String childPurchaseOrderId;
  private String commodityId;
  private String sourceDistrictGeoId;
  private String targetDistrictGeoId;

  @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ExternalMetaData> externalMetaDataSet = new HashSet<>();
}
