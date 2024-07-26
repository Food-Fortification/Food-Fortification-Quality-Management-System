package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.external.TargetLotsExternalRequestDto;
import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.*;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.SampleTestResult;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

public interface LotService {
    Long createLot(Long categoryId, LotRequestDto dto);

    ListResponse<LotResponseDto> getAllLotsByBatchId(Long batchId, Integer pageNumber, Integer pageSize);

    void updateLot(Long categoryId, LotRequestDto dto);

    List<StateResponseDto> getLotActions(Long categoryId, Long lotId, ActionType actionType, String sampleState);

    boolean updateLotState(Long categoryId, EntityStateRequestDTO entityStateRequestDTO, ActionType actionType, SampleTestResult sampleTestResult, Boolean isExternalTest);

    boolean receiveLot(LotReceiveRequestDto dto, Long categoryId, List<Long> ids);
    boolean acceptLot(LotReceiveRequestDto dto, Long categoryId, List<Long> ids);
    LotResponseDto getLotById(Long id, Long categoryId);

    void deleteLot(Long id);

    ListResponse<LotListResponseDTO> getAllLots(Long categoryId, Integer pageNumber, Integer pageSize, SearchListRequest searchListRequest);

    ListResponse<LotListResponseDTO> getLotInventory(Long categoryId, Integer pageNumber, Integer pageSize, Boolean approvedSourceLots, SearchListRequest searchRequest);

    ListResponse<LotResponseDto> getAllLotsBySourceCategoryId(Long categoryId, Long manufacturerId, String search, Integer pageNumber, Integer pageSize);

    LotResponseDto getDetailsForUUID(String uuid);

    Boolean checkLabAccess(Long lotId);

    void updateBatchInspectionStatus(Long lotId, Boolean isBlocked);

    List<Long> createTargetLotFromSourceLots(TargetLotRequestDto dto, Long categoryId, Boolean externalDispatch);

    Map<String, String> createTargetLotsFromSourceLots(TargetLotsExternalRequestDto dto, Long categoryId);


    void migrateData();

    LotHistoryResponseDto getHistoryForLot(Long lotId);

    List<LotResponseDto> getSourceLotsByTargetLotId(Long id);

    LotResponseDto getLotByIdForEventUpdate(Long id);
}
