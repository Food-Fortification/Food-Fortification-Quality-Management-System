package org.path.fortification.service.impl;

import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.entity.StateType;
import org.path.fortification.manager.StateManager;
import org.path.fortification.dto.requestDto.StateRequestDto;
import org.path.fortification.entity.State;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.StateMapper;
import org.path.fortification.dto.responseDto.StateResponseDto;
import org.path.fortification.service.StateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StateServiceImpl implements StateService {
    private final BaseMapper<StateResponseDto, StateRequestDto, State> mapper = BaseMapper.getForClass(StateMapper.class);
    private StateManager manager;

    @Override
    public void createState(StateRequestDto dto) {
        State entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public StateResponseDto getStateById(Long id) {
        State entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public Long getStateIdByName(String name) {
        State entity = manager.findByName(name);
        return entity.getId();
    }

    @Override
    public ListResponse<StateResponseDto> getAllStates(StateType type, Integer pageNumber, Integer pageSize) {
        List<State> entities;
        Long count;
        if (type!=null ){
            entities = manager.findAllByType(type,pageNumber,pageSize);
            count = manager.getCount(entities.size(), pageNumber, pageSize);
            return ListResponse.from(entities, mapper::toDto, count);
        }
        entities = manager.findAll(pageNumber, pageSize);
        count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateState(StateRequestDto dto) {
        State existingState = manager.findById(dto.getId());
        existingState.setName(dto.getName());
        if(dto.getName() != null) existingState.setName(dto.getName());
        if (dto.getDisplayName() != null) {
            existingState.setDisplayName(dto.getDisplayName());
        }
        if (dto.getActionName() != null) {
            existingState.setActionName(dto.getActionName());
        }
        manager.update(existingState);
    }

    @Override
    public void deleteState(Long id) {
        manager.delete(id);
    }
}
