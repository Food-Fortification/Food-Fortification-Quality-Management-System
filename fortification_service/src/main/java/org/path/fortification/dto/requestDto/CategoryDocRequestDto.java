package org.path.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDocRequestDto extends BaseRequestDto{
    private Long id;
    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
    @NotNull(message = "DocType id cannot be null")
    private Long docTypeId;
    private Boolean isMandatory;
    private Boolean isEnabled;
}
