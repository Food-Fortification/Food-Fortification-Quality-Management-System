package org.path.lab.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabListResponseDTO {
  private Long id;
  private String name;
  @JsonIncludeProperties(value = {"id","name"})
  private StatusResponseDto status;
  @JsonIncludeProperties(value = {"id","categoryId"})
  Set<LabCategoryResponseDto> labCategoryMapping;

}
