package org.path.lab.service;

import org.path.lab.dto.requestDto.LabTestRequestDTO;
import org.path.lab.dto.requestDto.CreateSampleRequestDto;
import org.path.lab.dto.responseDto.LabTestResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;

public interface LabTestService {
    ListResponse<LabTestResponseDTO> getAllLabTests(Integer pageNumber, Integer pageSize);
    LabTestResponseDTO getLabTestById(Long sampleId);
    void addLabTest(CreateSampleRequestDto createSampleRequestDto);
    void updateLabTestById(Long sampleId, LabTestRequestDTO labTestRequestDTO);
    void deleteLabTestById(Long sampleId);
    ListResponse<LabTestResponseDTO> getDetailsByBatchId(Long batchId,Integer pageNumber, Integer pageSize);
}
