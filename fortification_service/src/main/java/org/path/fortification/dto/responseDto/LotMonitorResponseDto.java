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
public class LotMonitorResponseDto extends LotListResponseDTO {
    @JsonIncludeProperties(value = {"uuid", "id", "name"})
    private CategoryResponseDto category;
    private BatchMonitorResponseDto sourceBatch;
    private Set<LotHistoryResponseDto> sourceLots = new HashSet<>();

    public void setLotProperties(LotListResponseDTO lotListResponseDTO) {
        BeanUtils.copyProperties(lotListResponseDTO, this, "batch");
    }
}
