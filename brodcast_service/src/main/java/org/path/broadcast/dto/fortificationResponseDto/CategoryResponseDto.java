package org.path.broadcast.dto.fortificationResponseDto;

import org.path.broadcast.dto.commonDtos.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
    private Boolean independentBatch;
    private Integer sequence;
    @JsonIncludeProperties(value = {"uuid", "id", "name", "description"})
    private ProductResponseDto product;
}
