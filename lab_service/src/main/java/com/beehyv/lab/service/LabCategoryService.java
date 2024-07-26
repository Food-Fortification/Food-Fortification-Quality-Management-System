package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.LabCategoryRequestDto;
import com.beehyv.lab.dto.responseDto.LabCategoryResponseDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import java.util.List;

public interface LabCategoryService {

    void create(LabCategoryRequestDto labCategoryRequestDto);
    LabCategoryResponseDto getById(Long id);

    void update(LabCategoryRequestDto labCategoryRequestDto);
    void delete(Long id);

    ListResponse<LabCategoryResponseDto> getByLabId(Long labId);
    List<Long> getCategoryIdsByLabId(Long labId);

}
