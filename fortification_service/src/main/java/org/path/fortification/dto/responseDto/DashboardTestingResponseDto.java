package org.path.fortification.dto.responseDto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DashboardTestingResponseDto extends BaseResponseDto {
    private Double batchTested;
    private Double lotApproved;
    private Double lotRejected;
    private Double lotInTransit;
    private Double availableTested;
    private Double batchRejected;
    private Double batchApproved;
    private Double totalDispatched;
    private Double sampleInTransit;
    private Double batchNotTested;
    private Long categoryId;
    private String categoryName;
    private String id;
}
