package org.path.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDto {
    private Long id;
    private String name;
    private String code;
    private String geoId;
}
