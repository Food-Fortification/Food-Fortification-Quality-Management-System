package org.path.fortification.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BatchDocResponseDto extends BaseResponseDto{
    private Long id;
    @JsonIncludeProperties(value = {"uuid", "id", "docType", "isMandatory"})
    private CategoryDocResponseDto categoryDoc;
    private String name;
    private String path;
    private Date expiry;
}
