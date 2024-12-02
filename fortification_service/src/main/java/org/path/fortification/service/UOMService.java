package org.path.fortification.service;

import org.path.fortification.dto.requestDto.UOMRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.UOMResponseDto;

public interface UOMService {
    void createUOM(UOMRequestDto dto);

    UOMResponseDto getUOMById(Long id);

    ListResponse<UOMResponseDto> getAllUOMs(Integer pageNumber, Integer pageSize);

    void updateUOM(UOMRequestDto dto);

    void deleteUOM(Long id);

}
