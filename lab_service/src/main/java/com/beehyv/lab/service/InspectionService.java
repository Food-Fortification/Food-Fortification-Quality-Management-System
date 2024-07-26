package com.beehyv.lab.service;

import com.beehyv.lab.dto.external.ExternalInspectionRequestDto;
import com.beehyv.lab.dto.requestDto.InspectionRequestDTO;
import com.beehyv.lab.dto.responseDto.InspectionResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.enums.SampleType;

public interface InspectionService {

  Long create(InspectionRequestDTO inspectionRequestDTO);
  Long createExternalInspection(ExternalInspectionRequestDto inspectionRequestDTO);
  InspectionResponseDTO getById(Long id);
  void delete (Long id);
  Long update(InspectionRequestDTO inspectionRequestDTO);
  ListResponse<Long> getAllIdsBySampleType(SampleType sampleType, Long categoryId, String sampleState, Integer pageNumber, Integer pageSize);
  Boolean getInspectionSampleStatus(SampleType sampleType, Long id);
}
