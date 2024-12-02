package org.path.fortification.dto.responseDto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProductionResponseDto extends BaseResponseDto{
    private Double totalProduction;
    private Double approvedQuantity;
    private Double availableTested;
    private Double availableNotTested;
    private Long categoryId;
    private String categoryName;
    private String id;
}
