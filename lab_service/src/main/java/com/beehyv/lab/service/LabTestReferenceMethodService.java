package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import com.beehyv.lab.dto.responseDto.LabMethodResponseDto;
import com.beehyv.lab.dto.responseDto.LabTestReferenceMethodResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabTestType;

import java.util.List;

public interface LabTestReferenceMethodService {
    List<LabTestReferenceMethodResponseDTO> getAllLabTestReferenceMethods(Integer pageNumber, Integer pageSize);
    List<LabTestReferenceMethodResponseDTO> getAllLabTestReferenceMethodsByCategoryId(Long categoryId , Integer pageNumber, Integer pageSize , LabTestType.Type type, Long manufacturerId);
    LabTestReferenceMethodResponseDTO getLabTestReferenceMethodById(Long labTestReferenceMethodId);
    void addLabTestReferenceMethod(LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO);
    void updateLabTestReferenceMethodById(Long labTestReferenceMethodId, LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO);
    void deleteLabTestReferenceMethodById(Long labTestReferenceMethodId);
    ListResponse<LabMethodResponseDto> getAllMethodsByCategoryId(Long categoryId, Long labSampleId, Integer pageNumber, Integer pageSize);
}
