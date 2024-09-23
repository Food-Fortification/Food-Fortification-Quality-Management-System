package com.beehyv.lab.dto.responseDto;

import com.beehyv.lab.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FssaiCertificateResponseDTO extends BaseResponseDTO {
    private String districtName;
    private String stateName;
    private Long labId;
    private Long pincode;
    private String labName;
    private Date certificateValidUpTo;
    private String contactPerson;
    private String address;
    private String certificateNo;
    private String contactMailPerson;
}
