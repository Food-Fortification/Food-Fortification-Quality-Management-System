package org.path.lab.service;

import org.path.lab.dto.requestDto.DashboardRequestDto;
import org.path.lab.dto.requestDto.LabRequestDTO;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.dto.responseDto.*;

import java.util.Map;

public interface LabService {
    ListResponse<LabResponseDTO> getAllLabs(SearchListRequest searchListRequest, String search, Integer pageNumber, Integer pageSize);
    LabResponseDTO getLabById(Long labId);
    Long createLab(LabRequestDTO labRequestDTO);
    void updateLabById(Long labId, LabRequestDTO labRequestDTO);
    void deleteLabById(Long labId);
    LabResponseDTO getNearestLab(String address, Long categoryId, Long manufacturerId);
    ListResponse<LabListResponseDTO> getAllActiveLabsForCategory(String search, Long manufacturerId, Long CategoryId, Integer pageNumber, Integer pageSize);
    Map<String, CategoryRoleResponseDto> getAllRoleCategoriesForLab(Long labId);

    ListResponse<LabDashboardResponseDto> getLabsAggregate(DashboardRequestDto dto);
    ListResponse<LabSampleDetailsResponseDto> getLabSamplesDetails(DashboardRequestDto dto, Long labId, String type);
    String findCertificateByName(String name);
}
