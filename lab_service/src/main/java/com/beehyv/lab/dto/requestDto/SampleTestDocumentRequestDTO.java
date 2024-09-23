package com.beehyv.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SampleTestDocumentRequestDTO {
    private Long id;
    private Long categoryDocId;
    private Long labSampleId;
    private String name;
    private String path;
}
