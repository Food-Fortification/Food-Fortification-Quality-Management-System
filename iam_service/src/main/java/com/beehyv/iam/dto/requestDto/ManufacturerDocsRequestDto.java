package com.beehyv.iam.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDocsRequestDto extends BaseRequestDto{
    private Long id;

    private Long manufacturerId;


    private Long categoryDocId;

    private String docName;


    private String docPath;
    private LocalDate docExpiry;
}
