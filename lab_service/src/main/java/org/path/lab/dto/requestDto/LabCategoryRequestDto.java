package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabCategoryRequestDto {
    private Long id;
    private Long categoryId;
    private Boolean isEnabled;
    private Long LabId;
}
