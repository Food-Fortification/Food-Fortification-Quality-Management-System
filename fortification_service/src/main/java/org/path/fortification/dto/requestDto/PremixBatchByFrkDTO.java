package org.path.fortification.dto.requestDto;

import org.path.fortification.dto.iam.AddressResponseDto;
import org.path.fortification.entity.SizeUnit;
import org.path.fortification.enums.VendorType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PremixBatchByFrkDTO extends BaseRequestDto{
    private Long vendorId;
    private String name;
    private String licenseNumber;
    private AddressResponseDto vendorAddress;
    private VendorType vendorType;
    private String batchName;
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date manufacturingDate;
    @FutureOrPresent(message = "Expiry Date must be greater than Current date")
    @JsonFormat(timezone = "IST", pattern = "yyyy-MM-dd")
    private Date expiryDate;
    private Long categoryId;
    private Double totalQuantity;
    private String manufacturerBatchNumber;
    private String reportNumber;
    private Long labId;
    private Date sampleTestDate;
    private Set<BatchDocRequestDto> batchDocRequestDtos;
    private Set<BatchPropertyRequestDto> batchProperties;
    private String createdBy;
    private Set<SizeUnit> sizeUnits;
}

