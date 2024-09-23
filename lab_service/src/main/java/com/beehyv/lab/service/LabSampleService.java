package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.LabSampleRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.LabNameAddressResponseDto;
import com.beehyv.lab.dto.responseDto.LabSampleCreateResponseDto;
import com.beehyv.lab.dto.responseDto.LabSampleResponseDto;
import com.beehyv.lab.dto.responseDto.LabSampleResultResponseDto;
import com.beehyv.lab.dto.responseDto.ListResponse;

import com.beehyv.lab.enums.SampleType;

import java.util.List;
import java.util.Map;

public interface LabSampleService {
    ListResponse<LabSampleResponseDto> getAllLabSamples(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest);

    LabSampleCreateResponseDto createLabSample(LabSampleRequestDTO labSampleRequestDTO, Boolean self);

    LabSampleResponseDto getLabSampleById(Long batchId);

    LabSampleResultResponseDto updateLabSample(LabSampleRequestDTO labSampleRequestDTO);

    void deleteLabSample(Long batchId);

    ListResponse<LabSampleResponseDto> getAllLabSamplesByBatchId(Long batchId);

    ListResponse<LabSampleResponseDto> getAllLabSamplesByLotId(Long lotId);
    Map<Long, LabNameAddressResponseDto> getAllLabSamplesByLotIds(SampleType sampleType, List<Long> lotIds);

    void updateSampleStatus(Long categoryId, Long sampleId, String state, String dateOfReceiving);

    LabNameAddressResponseDto getLabNameAddressByEntityId(SampleType entityType, Long entityId);

    List<LabSampleResponseDto> getAllLabSamplesByBatchIdForEventUpdate(Long batchId);

    List<LabSampleResponseDto> getAllLabSamplesByLotIdForEventUpdate(Long lotId);

    ListResponse<LabSampleResponseDto> getAllLabSamplesForSuperAdmins(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest);

}
