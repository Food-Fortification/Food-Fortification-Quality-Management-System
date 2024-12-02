package org.path.fortification.service;

import org.path.fortification.dto.requestDto.StatusRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StatusResponseDto;

public interface StatusService {
    void createStatus(StatusRequestDto dto);

    StatusResponseDto getStatusById(Long id);

    ListResponse<StatusResponseDto> getAllStatuses(Integer pageNumber, Integer pageSize);

    void updateStatus(StatusRequestDto dto);

    void deleteStatus(Long id);

}
