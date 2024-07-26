package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.StateRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.LocationResponseDto;
import com.beehyv.lab.dto.responseDto.StateResponseDTO;
import com.beehyv.lab.entity.State;
import com.beehyv.lab.manager.StateManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.StateService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StateServiceImpl implements StateService {

    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final StateManager stateManager;

    @Override
    public void create(StateRequestDTO stateRequestDTO) {
      stateManager.create(mapper.mapDtoToEntityState(stateRequestDTO));
    }

    @Override
    public StateResponseDTO getById(Long id) {
        State state = stateManager.findById(id);
        if(state != null) {
            return mapper.mapToResponseDto(state);
        } else {
            return null;
        }
    }

    @Override
    public void update(StateRequestDTO stateRequestDTO) {
        stateManager.update(mapper.mapDtoToEntityState(stateRequestDTO));
    }

    @Override
    public void delete(Long id) {
        stateManager.delete(id);
    }

    @Override
    public ListResponse<StateResponseDTO> findAll(Integer pageNumber,Integer pageSize){
        List<State> entities = stateManager.findAll(pageNumber,pageSize);
        Long count = stateManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities, mapper::mapToResponseDto,count);
    }

    public ListResponse<LocationResponseDto> getAllStatesByCountryId(Long countryId, String search, Integer pageNumber, Integer pageSize){
        List<State> entities = stateManager.findAllByCountryId(countryId, search, pageNumber,pageSize);
        Long count = stateManager.getCountForCountryIdAndSearch(countryId, search);
        return ListResponse.from(entities, mapper::mapToDto,count);
    }

}
