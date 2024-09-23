package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.StateRequestDto;
import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.entity.StateType;

import java.util.List;

public interface StateService {
    void createState(StateRequestDto dto);

    StateResponseDto getStateById(Long id);

    Long getStateIdByName(String name);

    ListResponse<StateResponseDto> getAllStates(StateType type, Integer pageNumber, Integer pageSize);

    void updateState(StateRequestDto dto);

    void deleteState(Long id);

}
