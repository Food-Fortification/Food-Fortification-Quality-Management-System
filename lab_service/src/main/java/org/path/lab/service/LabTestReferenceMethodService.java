package org.path.lab.service;

import org.path.lab.dto.requestDto.LabTestReferenceMethodRequestDTO;
import org.path.lab.dto.responseDto.LabMethodResponseDto;
import org.path.lab.dto.responseDto.LabTestReferenceMethodResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.LabTestType;

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
