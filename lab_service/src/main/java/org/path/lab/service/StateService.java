package org.path.lab.service;

import org.path.lab.dto.requestDto.StateRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.LocationResponseDto;
import org.path.lab.dto.responseDto.StateResponseDTO;

public interface StateService {

    void create(StateRequestDTO stateRequestDTO);
    StateResponseDTO getById(Long id);

    void update(StateRequestDTO stateRequestDTO);

    void delete(Long id);

    ListResponse<StateResponseDTO> findAll(Integer pageNumber,Integer pageSize);

    ListResponse<LocationResponseDto> getAllStatesByCountryId(Long countryId, String search, Integer pageNumber, Integer pageSize);

}
