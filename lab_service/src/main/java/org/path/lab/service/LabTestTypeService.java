package org.path.lab.service;

import org.path.lab.dto.requestDto.LabTestTypeRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.LabTestTypeResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.LabTestType;

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
