package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.SizeUnitRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.SizeUnitResponseDto;

import java.util.List;

public interface SizeUnitService {
    void createSizeUnit(SizeUnitRequestDto dto);

    SizeUnitResponseDto getSizeUnitById(Long id);

    ListResponse<SizeUnitResponseDto> getAllSizeUnits(Long batchId, Integer pageNumber, Integer pageSize);

    void updateSizeUnit(SizeUnitRequestDto dto);

    void deleteSizeUnit(Long id);
    boolean createSizeUnits(List<SizeUnitRequestDto> sizeUnits,Long batchId);
    boolean updateSizeUnits(List<SizeUnitRequestDto> sizeUnits,Long batchId);

}
