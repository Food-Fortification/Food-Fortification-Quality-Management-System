package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.StatusRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.StatusResponseDto;
import org.path.lab.entity.Status;
import org.path.lab.manager.StatusManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.StatusService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusServiceImpl implements StatusService {
  private final StatusManager statusManager;
  private final DTOMapper dtoMapper;

  public StatusResponseDto getById(Long id) {
    return dtoMapper.mapToResponseDto(statusManager.findById(id));
  }

  public Long create(StatusRequestDTO dto) {
    return statusManager.create(dtoMapper.mapToEntity(dto)).getId();
  }

  public void update(StatusRequestDTO dto) {
    statusManager.update(dtoMapper.mapToEntity(dto));
  }

  public void delete(Long id) {
    statusManager.delete(id);
  }

  public ListResponse<StatusResponseDto> findAll(Integer pageNumber,Integer pageSize){
    List<Status> entities = statusManager.findAll(pageNumber,pageSize);
    Long count = statusManager.getCount(entities.size(),pageNumber,pageSize);
    return ListResponse.from(entities, dtoMapper::mapToResponseDto,count);
  }
}