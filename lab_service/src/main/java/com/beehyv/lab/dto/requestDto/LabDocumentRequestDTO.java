package com.beehyv.lab.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LabDocumentRequestDTO {
    private Long id;
    private String name;
    private Long categoryDocId;
    private String path;
    private Long labId;
}
