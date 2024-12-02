package org.path.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabManufacturerRequestDTO {


    private Long id;
    private Long manufacturerId;
    private Long labId;
    private Long categoryId;
}
