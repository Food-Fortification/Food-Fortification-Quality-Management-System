package com.beehyv.iam.dto.external;

import com.beehyv.iam.dto.responseDto.BaseResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseOrderResponseDto extends BaseResponseDto {

  private Long id;
  private Long sourceManufacturerId;
  private Long targetManufacturerId;
  private String purchaseOrderId;
  private Double maxDispatchableQuantity;
  private Double dispatchedQuantity;
  private String sourceManufacturerLicenseId;
  private String targetManufacturerLicenseId;
}
