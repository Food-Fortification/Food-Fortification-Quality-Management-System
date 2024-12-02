package org.path.fortification.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchPropertyRequestDto extends BaseRequestDto {
    private Long id;
    @NotNull(message = "Batch id cannot be null")
    private Long batchId;
    private String name;
    private String value;
}
