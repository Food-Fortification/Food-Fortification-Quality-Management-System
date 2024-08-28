package com.beehyv.Immudb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NameAddressResponseDto {
    private String name;
    private String completeAddress;
}
