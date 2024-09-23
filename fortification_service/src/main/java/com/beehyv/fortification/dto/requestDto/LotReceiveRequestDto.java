package com.beehyv.fortification.dto.requestDto;

import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LotReceiveRequestDto {
  private String lotNo;
  private String comments;
  private Date dateOfAction;
  private Double acknowledgedQuantity;
  private String externalTargetManufacturerId;
}
