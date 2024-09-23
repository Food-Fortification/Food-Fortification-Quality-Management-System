package com.beehyv.iam.dto.requestDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchListRequest {
    private List<SearchCriteria> searchCriteriaList;
}
