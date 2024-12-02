package org.path.fortification.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MixMappingCommentsRequestDto {

    List<MixMappingRequestDto> mixMappingRequestDtoList;
    String comments;
}
