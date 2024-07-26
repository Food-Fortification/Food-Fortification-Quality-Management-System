package com.beehyv.iam.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ChildPurchaseOrderResponseDto {
    private String childPurchaseOrderId;
    private Double maxDispatchableQuantity;
    private String commodityId;
    private String commodityType;
}
