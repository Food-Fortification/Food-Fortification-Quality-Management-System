package org.path.fortification.dto.responseDto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class EmpanelledAggregatesResponseDto extends BaseResponseDto{
    private Double approved;
    private Double rejected;
    private Double transit;
    private Double dispatched;
    private Double available;
    private Long categoryId;
    private String categoryName;
    private String geoId;
    private Double receivedLots;
}
