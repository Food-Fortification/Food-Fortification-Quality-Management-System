package com.beehyv.iam.dto.external;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FssaiResponseDto {
    private String statusCode;
    private String redirectionUrl;
}
