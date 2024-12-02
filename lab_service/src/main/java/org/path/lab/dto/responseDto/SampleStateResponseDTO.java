package org.path.lab.dto.responseDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SampleStateResponseDTO extends BaseResponseDTO{
    private Long id;
    private String name;
    private String displayName;
}
