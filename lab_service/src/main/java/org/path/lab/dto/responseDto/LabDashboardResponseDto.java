package org.path.lab.dto.responseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LabDashboardResponseDto extends BaseResponseDTO{
    private Long id;
    private String name;
    private String state;
    private String district;
    private Long samplesReceived;
    private Long samplesRejected;
    private Long samplesInLab;
    private Long samplesToReceive;
    private Long testsDone;
    private String nablCertificate;
}
