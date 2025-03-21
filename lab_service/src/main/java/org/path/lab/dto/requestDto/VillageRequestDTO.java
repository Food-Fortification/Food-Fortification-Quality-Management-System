package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VillageRequestDTO {
    private Long id;
    private String name;
    private Long districtId;
}
