package org.path.fortification.service;

import org.path.fortification.dto.requestDto.StateRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StateResponseDto;
import org.path.fortification.entity.StateType;

public interface StateService {
    void createState(StateRequestDto dto);

    StateResponseDto getStateById(Long id);

    Long getStateIdByName(String name);

    ListResponse<StateResponseDto> getAllStates(StateType type, Integer pageNumber, Integer pageSize);

    void updateState(StateRequestDto dto);

    void deleteState(Long id);

}
