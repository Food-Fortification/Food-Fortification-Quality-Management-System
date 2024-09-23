package com.beehyv.broadcast.dto.labResponseDto;

import com.beehyv.broadcast.dto.commonDtos.BaseResponseDto;
import com.beehyv.broadcast.dto.commonDtos.StatusResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabResponseDTO extends BaseResponseDto {
    private Long id;
    private String name;
    private String description;
    private String completeAddress;
    @JsonIgnoreProperties(value = {"description"})
    private StatusResponseDto status;
}
