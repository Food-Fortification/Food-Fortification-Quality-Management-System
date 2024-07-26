package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.SampleStateRequestDto;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.SampleStateResponseDTO;
import com.beehyv.lab.entity.SampleState;
import com.beehyv.lab.manager.SampleStateManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.SampleStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SampleStateServiceImpl implements SampleStateService {
    private final DTOMapper dtoMapper;
    private final SampleStateManager sampleStateManager;
    @Override
    public void create(SampleStateRequestDto sampleStateDto) {
        SampleState sampleState = dtoMapper.mapDtoToEntitySampleState(sampleStateDto);
        sampleStateManager.create(sampleState);
    }

    @Override
    public SampleStateResponseDTO getById(Long id) {
        SampleState sampleState = sampleStateManager.findById(id);
        return dtoMapper.mapEntityToDtoSampleState(sampleState);
    }

    @Override
    public void update(SampleStateRequestDto sampleStateDto) {
        SampleState sampleState = dtoMapper.mapDtoToEntitySampleState(sampleStateDto);
        sampleStateManager.update(sampleState);
    }

    @Override
    public void delete(Long id) {
    sampleStateManager.delete(id);
    }

    @Override
    public ListResponse<SampleStateResponseDTO> getAll(Integer pageNumber, Integer pageSize) {
        List<SampleState> entities = sampleStateManager.findAll(pageNumber,pageSize);
        Long count = sampleStateManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities,dtoMapper::mapEntityToDtoSampleState,count);
    }
}
