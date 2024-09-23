package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.LabRequestDTO;
import com.beehyv.lab.dto.requestDto.StatusRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.StatusResponseDto;
import com.beehyv.lab.entity.Status;
import com.beehyv.lab.manager.StatusManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.StatusService;
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