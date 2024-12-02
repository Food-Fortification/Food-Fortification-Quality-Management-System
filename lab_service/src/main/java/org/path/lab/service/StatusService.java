package org.path.lab.service;


import org.path.lab.dto.requestDto.StatusRequestDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.dto.responseDto.StatusResponseDto;

public interface StatusService {
  StatusResponseDto getById(Long id);
  Long create(StatusRequestDTO dto);
  void update(StatusRequestDTO dto);
  void delete(Long id);
  ListResponse<StatusResponseDto> findAll(Integer pageNumber,Integer pageSize);


}
