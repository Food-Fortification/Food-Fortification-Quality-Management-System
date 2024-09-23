package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.UOMRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.UOMResponseDto;

import java.util.List;

public interface UOMService {
    void createUOM(UOMRequestDto dto);

    UOMResponseDto getUOMById(Long id);

    ListResponse<UOMResponseDto> getAllUOMs(Integer pageNumber, Integer pageSize);

    void updateUOM(UOMRequestDto dto);

    void deleteUOM(Long id);

}
