package org.path.fortification.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DSLDto {

    private String product;

    @JsonProperty("platform_name")
    private String platformName;

    private String description;

    private List<CategoryDslDto> categories;

    private List<String> stages;

    private List<WorkflowDto> workflow;
}