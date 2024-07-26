package com.beehyv.iam.dto.external;

import com.beehyv.iam.dto.requestDto.BaseRequestDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BufferGodownDetailRequestDto extends BaseRequestDto {
    private Long id;
    private String bufferGodownId;
    private Double allotedQty;
    private String moDestinationId;
}
