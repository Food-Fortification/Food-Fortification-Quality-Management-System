package com.beehyv.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NameAddressResponseDto {
    private Long id;
    private String name;
    private String completeAddress;
    private String licenseNo;
}
