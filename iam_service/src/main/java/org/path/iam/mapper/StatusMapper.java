package org.path.iam.mapper;

import org.path.iam.dto.responseDto.StatusResponseDto;
import org.path.iam.dto.requestDto.StatusRequestDto;
import org.path.iam.model.Status;

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
