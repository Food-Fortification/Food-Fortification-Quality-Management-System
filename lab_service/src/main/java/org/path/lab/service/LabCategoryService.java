package org.path.lab.service;

import org.path.lab.dto.requestDto.LabCategoryRequestDto;
import org.path.lab.dto.responseDto.LabCategoryResponseDto;
import org.path.lab.dto.responseDto.ListResponse;
import java.util.List;

public interface LabCategoryService {

    void create(LabCategoryRequestDto labCategoryRequestDto);
    LabCategoryResponseDto getById(Long id);

    void update(LabCategoryRequestDto labCategoryRequestDto);
    void delete(Long id);

    ListResponse<LabCategoryResponseDto> getByLabId(Long labId);
    List<Long> getCategoryIdsByLabId(Long labId);

}
