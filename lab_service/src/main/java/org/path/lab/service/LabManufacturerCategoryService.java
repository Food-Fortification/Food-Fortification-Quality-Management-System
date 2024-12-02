package org.path.lab.service;

import org.path.lab.dto.requestDto.LabManufacturerRequestDTO;
import org.path.lab.dto.responseDto.LabListResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;

public interface LabManufacturerCategoryService {


    void create(LabManufacturerRequestDTO labManufacturerRequestDTO);

    void delete(Long id);

    ListResponse<LabListResponseDTO> getLabsByManufacturerId(String search,Long manufacturerId, Integer pageNumber, Integer pageSize);
}
