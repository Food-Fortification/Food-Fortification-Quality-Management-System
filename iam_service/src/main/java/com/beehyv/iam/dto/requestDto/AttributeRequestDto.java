package com.beehyv.iam.dto.requestDto;

import com.beehyv.iam.enums.AttributeScoreType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttributeRequestDto extends BaseRequestDto{

    private Long id;
    private String name;
    private Boolean isActive;
    private Double weightage;
    private Integer totalScore;
    private Integer defaultScore;
    private AttributeScoreType type;
    private Long attributeCategoryId;
}
