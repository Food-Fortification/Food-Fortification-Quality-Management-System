package org.path.fortification.service.impl;

import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.manager.StatusManager;
import org.path.fortification.dto.requestDto.StatusRequestDto;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.entity.Status;
import org.path.fortification.mapper.StatusMapper;
import org.path.fortification.dto.responseDto.StatusResponseDto;
import org.path.fortification.service.StatusService;
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
