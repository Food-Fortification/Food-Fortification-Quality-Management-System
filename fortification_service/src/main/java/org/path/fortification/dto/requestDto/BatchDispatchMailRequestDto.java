package org.path.fortification.dto.requestDto;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchDispatchMailRequestDto extends BaseRequestDto{
    private String sourceManufacturer;
    private String targetManufacturer;
    private String batchNo;
    private String lotNo;
    private String batchName;
    private Date dateOfDispatch;
    private Date dateOfManufacture;
    private Date dateOfExpiry;
}
