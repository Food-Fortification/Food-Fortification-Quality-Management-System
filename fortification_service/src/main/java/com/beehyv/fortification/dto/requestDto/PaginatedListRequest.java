package com.beehyv.fortification.dto.requestDto;

import lombok.*;
import org.springframework.data.domain.PageRequest;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedListRequest {
    private Integer pageSize;
    private Integer pageNumber;
}
