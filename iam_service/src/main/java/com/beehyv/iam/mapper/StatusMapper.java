package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.responseDto.StatusResponseDto;
import com.beehyv.iam.dto.requestDto.StatusRequestDto;
import com.beehyv.iam.model.Status;

public class StatusMapper implements Mappable<StatusResponseDto, StatusRequestDto, Status> {

    public StatusResponseDto toDto(Status entity) {
        return new StatusResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public Status toEntity(StatusRequestDto dto) {
        return new Status(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }
}
