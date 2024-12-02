package org.path.lab.service;

import org.path.lab.dto.requestDto.InspectionRequestDTO;
import org.path.lab.dto.responseDto.InspectionResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.enums.SampleType;

public interface InspectionService {

  Long create(InspectionRequestDTO inspectionRequestDTO);
  InspectionResponseDTO getById(Long id);
  void delete (Long id);
  Long update(InspectionRequestDTO inspectionRequestDTO);
  ListResponse<Long> getAllIdsBySampleType(SampleType sampleType, Long categoryId, String sampleState, Integer pageNumber, Integer pageSize);
  Boolean getInspectionSampleStatus(SampleType sampleType, Long id);
}
