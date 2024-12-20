package org.path.fortification.dto.requestDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private Object value;
}