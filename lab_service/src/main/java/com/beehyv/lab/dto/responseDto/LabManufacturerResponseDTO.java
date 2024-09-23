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
public class LabManufacturerResponseDTO {

  private Long id;
  private Long categoryId;
  private Long manufacturerId;
  @JsonIgnoreProperties(value = {"labDocs","address"})
  private LabResponseDTO lab;
}
