package org.path.iam.service;

import org.path.iam.dto.requestDto.StateRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.LocationResponseDto;
import org.path.iam.dto.responseDto.StateResponseDto;
import org.path.iam.manager.StateManager;
import org.path.iam.model.State;
import org.path.iam.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StateService {
    private final StateManager stateManager;
    private final DtoMapper dtoMapper;

    public void create(StateRequestDto dto){
        stateManager.create(dtoMapper.mapToEntity(dto));
    }

    public void update(StateRequestDto dto){
        stateManager.update(dtoMapper.mapToEntity(dto));
    }

    public StateResponseDto getById(Long id){
        State state = stateManager.findById(id);
        return dtoMapper.mapToResponseDto(state);
    }
    public ListResponse<StateResponseDto> findAll(Integer pageNumber,Integer pageSize){
        List<State> entities = stateManager.findAll(pageNumber,pageSize);
        Long count = stateManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities, dtoMapper::mapToResponseDto,count);
    }
    public void delete(Long id){
        stateManager.delete(id);
    }
    public ListResponse<LocationResponseDto> getAllStatesByCountryId(Long countryId, String search, Integer pageNumber, Integer pageSize){
        List<State> entities = stateManager.findAllByCountryId(countryId, search, pageNumber,pageSize);
        Long count = stateManager.getCountForCountryIdAndSearch(countryId, search);
        return ListResponse.from(entities, dtoMapper::mapToDto,count);
    }
}
