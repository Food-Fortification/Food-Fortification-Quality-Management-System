package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.LabTestRequestDTO;
import com.beehyv.lab.dto.requestDto.CreateSampleRequestDto;
import com.beehyv.lab.dto.responseDto.LabTestDetailsResponseDto;
import com.beehyv.lab.dto.responseDto.LabTestResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;

import java.util.List;

public interface LabTestService {
    ListResponse<LabTestResponseDTO> getAllLabTests(Integer pageNumber, Integer pageSize);
    LabTestResponseDTO getLabTestById(Long sampleId);
    void addLabTest(CreateSampleRequestDto createSampleRequestDto);
    void updateLabTestById(Long sampleId, LabTestRequestDTO labTestRequestDTO);
    void deleteLabTestById(Long sampleId);
    ListResponse<LabTestResponseDTO> getDetailsByBatchId(Long batchId,Integer pageNumber, Integer pageSize);
}
