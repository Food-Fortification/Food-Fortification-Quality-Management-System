package com.beehyv.fortification.dto.responseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class LabNameAddressResponseDto {
  private Long id;
  private String name;
  private String completeAddress;
  private String labCertificateNumber;
}