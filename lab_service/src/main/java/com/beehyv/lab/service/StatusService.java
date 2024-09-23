package com.beehyv.lab.service;


import com.beehyv.lab.dto.requestDto.StatusRequestDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.dto.responseDto.StatusResponseDto;

public interface StatusService {
  StatusResponseDto getById(Long id);
  Long create(StatusRequestDTO dto);
  void update(StatusRequestDTO dto);
  void delete(Long id);
  ListResponse<StatusResponseDto> findAll(Integer pageNumber,Integer pageSize);


}
