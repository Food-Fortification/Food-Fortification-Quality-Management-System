package org.path.iam.dto.requestDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkUploadRequestDto extends BaseRequestDto{
    private String name;
    private String completeAddress;
    private String licenseNumber;
    private String type;

    private String status;
}
