package org.path.fortification.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto extends BaseRequestDto {
    private Long id;
    private String name;
    @NotNull(message = "Product id cannot be null")
    private Long productId;
    private boolean independentBatch;
    private Integer sequence;
    private Set<Long> sourceCategoryIds;
    private Set<CategoryDocRequestDto> documents;
}
