package com.beehyv.lab.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabCategoryResponseDto {
    private Long id;
    private Long categoryId;
    private Boolean isEnabled;
    @JsonIgnoreProperties(value = {"labDocs","address"})
    private LabResponseDTO lab;
}
