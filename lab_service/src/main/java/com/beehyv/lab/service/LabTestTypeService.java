package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.LabTestTypeRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.LabTestTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabTestType;

import java.util.List;

public interface LabTestTypeService {
    List<LabTestTypeResponseDTO> getLabTestTypesByCategoryId(Long categoryId, String geoId, Integer pageNumber, Integer pageSize);
    List<LabTestTypeResponseDTO> getLabTestTypesByType(LabTestType.Type type, Long categoryId,Integer pageNumber, Integer pageSize);
    ListResponse<LabTestTypeResponseDTO> getAllLabTestTypes(
        SearchListRequest searchListRequest,Integer pageNumber, Integer pageSize);
    LabTestTypeResponseDTO getLabTestTypeById(Long labTestTypeId);
    void addLabTestType(LabTestTypeRequestDTO labTestTypeRequestDTO);
    void updateLabTestTypeById(Long labTestTypeId, LabTestTypeRequestDTO labTestTypeRequestDTO);
    void deleteLabTestTypeById(Long labTestTypeId);
}
