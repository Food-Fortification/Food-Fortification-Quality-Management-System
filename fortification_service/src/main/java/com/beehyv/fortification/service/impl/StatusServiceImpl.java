package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.manager.StatusManager;
import com.beehyv.fortification.dto.requestDto.StatusRequestDto;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.entity.Status;
import com.beehyv.fortification.mapper.StatusMapper;
import com.beehyv.fortification.dto.responseDto.StatusResponseDto;
import com.beehyv.fortification.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StatusServiceImpl implements StatusService {
    private final BaseMapper<StatusResponseDto, StatusRequestDto, Status> mapper = BaseMapper.getForClass(StatusMapper.class);
    private StatusManager manager;

    @Override
    public void createStatus(StatusRequestDto dto) {
        Status entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public StatusResponseDto getStatusById(Long id) {
        Status entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<StatusResponseDto> getAllStatuses(Integer pageNumber, Integer pageSize) {
        List<Status> entities = manager.findAll(pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateStatus(StatusRequestDto dto) {
        Status existingStatus = manager.findById(dto.getId());
        existingStatus.setName(dto.getName());
        existingStatus.setDescription(dto.getDescription());
        manager.update(existingStatus);
    }

    @Override
    public void deleteStatus(Long id) {
        manager.delete(id);
    }
}
