package org.path.iam.service;

import org.path.iam.dto.requestDto.StatusRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.StatusResponseDto;
import org.path.iam.manager.StatusManager;
import org.path.iam.model.Status;
import org.path.iam.utils.DtoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {
  private final StatusManager statusManager;
  private final DtoMapper dtoMapper;

  public StatusResponseDto getById(Long id) {
    return dtoMapper.mapToResponseDto(statusManager.findById(id));
  }

  public Long create(StatusRequestDto dto) {
    return statusManager.create(dtoMapper.mapToEntity(dto)).getId();
  }

  public void update(StatusRequestDto dto) {
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
