package org.path.broadcast.dto.fortificationResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDocResponseDto extends BaseResponseDto {
    private Long id;
    private Long categoryId;
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private DocTypeResponseDto docType;
    private Boolean isMandatory;

    private Boolean isEnabled;
}
