package org.path.fortification.service;

import org.path.fortification.dto.requestDto.SizeUnitRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.SizeUnitResponseDto;

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
