package com.beehyv.iam.dto.external;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailsRequestDto {
    private String application_no;
    private String license_no;
    private String fbo_id;
    private String fbo_name;
    private Date expiry_date;
    private String status;
}
