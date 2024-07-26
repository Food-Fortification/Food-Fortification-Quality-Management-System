package com.beehyv.iam.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDocsResponseDto extends BaseResponseDto{
    private Long id;
    private CategoryDocResponseDto categoryDoc;
    private String docName;
    private String docPath;
    private LocalDate docExpiry;
}
