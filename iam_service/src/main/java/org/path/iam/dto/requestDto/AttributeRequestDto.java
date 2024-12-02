package org.path.iam.dto.requestDto;

import org.path.iam.enums.AttributeScoreType;
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
