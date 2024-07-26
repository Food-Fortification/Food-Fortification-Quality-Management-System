package com.beehyv.lab.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AggregatedResponseDto<T> {
    private List<T> data;
}
