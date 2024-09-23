package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.StatusRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.StatusResponseDto;

import java.util.List;

public interface StatusService {
    void createStatus(StatusRequestDto dto);

    StatusResponseDto getStatusById(Long id);

    ListResponse<StatusResponseDto> getAllStatuses(Integer pageNumber, Integer pageSize);

    void updateStatus(StatusRequestDto dto);

    void deleteStatus(Long id);

}
