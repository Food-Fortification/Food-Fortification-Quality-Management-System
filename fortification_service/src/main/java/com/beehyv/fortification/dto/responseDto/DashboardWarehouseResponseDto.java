package com.beehyv.fortification.dto.responseDto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DashboardWarehouseResponseDto extends BaseResponseDto {
    private Double accepted;
    private Double rejected;
    private Double dispatched;
    private Double available;
    private String id;
}
