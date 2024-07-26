package com.beehyv.lab.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // limit, offset, count, data // move to parent
public class ListResponse<R> {
    private Long count;
    private List<R> data;

    public <T> ListResponse(List<T> data, Function<? super T, ? extends R> mapper, Long count) {
        this.count = count;
        this.data = data.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T, R> ListResponse<R> from(List<T> data, Function<? super T, ? extends R> mapper, Long count) {
        return new ListResponse<R>(data, mapper, count);
    }
    public ListResponse(List<R> data, Long count) {
        this.count = count;
        this.data = data;
    }
}
