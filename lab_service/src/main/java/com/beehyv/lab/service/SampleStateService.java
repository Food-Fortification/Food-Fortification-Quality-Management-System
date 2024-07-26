package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.SampleStateRequestDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.SampleStateResponseDTO;

public interface SampleStateService {

    void create(SampleStateRequestDto sampleStateDto);

    SampleStateResponseDTO getById(Long id);

    void update(SampleStateRequestDto sampleStateDto);

    void delete(Long id);

    ListResponse<SampleStateResponseDTO> getAll(Integer pageNumber, Integer pageSize);
}
