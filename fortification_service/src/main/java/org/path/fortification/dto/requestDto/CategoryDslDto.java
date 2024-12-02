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
public class CategoryDslDto {
    private String name;

    @JsonProperty("outside_platform")
    private boolean outsidePlatform;

    private String type;

    @JsonProperty("raw_materials")
    private List<String> rawMaterials;

    private List<TargetDto> target;

    @JsonProperty("dispatch_lab_option")
    private String dispatchLabOption;

}
