package com.beehyv.fortification.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import java.util.Set;

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
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private Set<CategoryResponseDto> sourceCategories;
    @JsonIncludeProperties(value = {"uuid", "id", "docType", "isMandatory"})
    private Set<CategoryDocResponseDto> documents;
}
