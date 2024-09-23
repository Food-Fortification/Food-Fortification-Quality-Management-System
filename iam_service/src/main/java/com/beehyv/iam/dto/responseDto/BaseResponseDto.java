package com.beehyv.iam.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {
//    protected String createdBy;
//
//    protected LocalDateTime createdDate;
//
//    protected String modifiedBy;
//
//    protected LocalDateTime modifiedDate;
      protected String uuid;
    @JsonIncludeProperties(value = {"id", "name"})
    protected StatusResponseDto status;
}
