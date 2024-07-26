package com.beehyv.iam.dto.external;

import com.beehyv.iam.dto.requestDto.BaseRequestDto;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseOrderRequestDto extends BaseRequestDto {
    private Long id;
    private String moTransactionId;
    private String manufacturingid;
    private String districtId;
    private Double totalAllotedQty;
    private Integer allotedMonth;
    private String dmOrderNo;
    private Date dmorderDate;
    private Date dmDeliverDate;
    private String commodityId;
    Set<ExternalMetaDataRequestDto> externalMetaDataRequestDtos = new HashSet<>();
    private Set<BufferGodownDetailRequestDto> bufferGodownDetails;
}
