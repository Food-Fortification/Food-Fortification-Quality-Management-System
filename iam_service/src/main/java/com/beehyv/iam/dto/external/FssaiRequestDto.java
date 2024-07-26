package com.beehyv.iam.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FssaiRequestDto {
    private String requestID;
    private UserDetailsRequestDto userDetails;
}
