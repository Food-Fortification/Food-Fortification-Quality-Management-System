package org.path.lab.service;

import org.path.lab.dto.requestDto.SampleStateRequestDto;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.SampleStateResponseDTO;

public interface SampleStateService {

    void create(SampleStateRequestDto sampleStateDto);

    SampleStateResponseDTO getById(Long id);

    void update(SampleStateRequestDto sampleStateDto);

    void delete(Long id);

    ListResponse<SampleStateResponseDTO> getAll(Integer pageNumber, Integer pageSize);
}
