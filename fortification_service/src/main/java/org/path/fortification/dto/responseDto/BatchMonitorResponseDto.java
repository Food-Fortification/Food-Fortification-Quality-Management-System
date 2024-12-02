package org.path.fortification.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchMonitorResponseDto extends BatchListResponseDTO {
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private CategoryResponseDto category;
    private Set<MixMappingMonitorResponseDto> mixes = new HashSet<>();

    public void setBatchProperties(BatchListResponseDTO batchListResponseDTO) {
        BeanUtils.copyProperties(batchListResponseDTO, this);
    }
}
