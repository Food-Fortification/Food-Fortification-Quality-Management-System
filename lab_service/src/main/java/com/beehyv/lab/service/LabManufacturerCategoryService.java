package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.LabManufacturerRequestDTO;
import com.beehyv.lab.dto.responseDto.LabListResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;

public interface LabManufacturerCategoryService {


    void create(LabManufacturerRequestDTO labManufacturerRequestDTO);

    void delete(Long id);

    ListResponse<LabListResponseDTO> getLabsByManufacturerId(String search,Long manufacturerId, Integer pageNumber, Integer pageSize);
}
