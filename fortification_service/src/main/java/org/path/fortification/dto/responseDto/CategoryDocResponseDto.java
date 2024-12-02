package org.path.fortification.dto.responseDto;

import org.path.fortification.entity.DocType;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDocResponseDto extends BaseResponseDto{
    private Long id;
    private Long categoryId;
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private DocType docType;
    private Boolean isMandatory;

    private Boolean isEnabled;
}
