package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.DashboardRequestDto;
import com.beehyv.lab.dto.requestDto.LabRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.*;

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
