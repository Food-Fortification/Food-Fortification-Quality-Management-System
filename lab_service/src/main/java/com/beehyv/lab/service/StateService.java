package com.beehyv.lab.service;

import com.beehyv.lab.dto.requestDto.StateRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.dto.responseDto.StateResponseDTO;

public interface StateService {

    void create(StateRequestDTO stateRequestDTO);
    StateResponseDTO getById(Long id);

    void update(StateRequestDTO stateRequestDTO);

    void delete(Long id);

    ListResponse<StateResponseDTO> findAll(Integer pageNumber,Integer pageSize);

    ListResponse<LocationResponseDto> getAllStatesByCountryId(Long countryId, String search, Integer pageNumber, Integer pageSize);

}
