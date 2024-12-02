package org.path.iam.mapper;

import org.path.iam.dto.requestDto.BaseRequestDto;
import org.path.iam.dto.responseDto.BaseResponseDto;
import org.path.iam.dto.responseDto.StatusResponseDto;
import org.path.iam.model.Base;
import org.path.iam.exception.MapperException;
import org.path.iam.model.Status;

public class BaseMapper<ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base> {
    Mappable<ResponseDto, RequestDto, E> mapper;

    public <M extends Mappable<ResponseDto, RequestDto, E>> BaseMapper(Class<M> mappableClass) throws MapperException {
        try {
            this.mapper = mappableClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }

    public static <ResponseDto extends BaseResponseDto, RequestDto extends BaseRequestDto, E extends Base, M extends Mappable<ResponseDto, RequestDto, E>>
    BaseMapper<ResponseDto, RequestDto, E> getForClass(Class<M> mappableClass) {
        return new BaseMapper<>(mappableClass);
    }

    // Map Base JPA Entity into given dto
    public void toBaseDto(E entity, ResponseDto dto) {
        if(entity == null) return;
//        dto.setCreatedBy(entity.getCreatedBy());
//        dto.setModifiedBy(entity.getModifiedBy());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setModifiedDate(entity.getModifiedDate());
        if (entity.getStatus() != null && !(entity instanceof Status)) {
            StatusResponseDto statusResponseDto = new StatusResponseDto();
            statusResponseDto.setId(entity.getStatus().getId());
            statusResponseDto.setName(entity.getStatus().getName());
            statusResponseDto.setDescription(entity.getStatus().getDescription());
            dto.setStatus(statusResponseDto);
        }
        if (entity.getUuid() != null) dto.setUuid(entity.getUuid());
    }

    // Map given dto into given JPA Entity
    public void toBaseEntity(RequestDto dto, E entity) {
        if (dto.getStatusId() != null) {
            Status status = new Status();
            status.setId(dto.getStatusId());
            entity.setStatus(status);
        }
    }

    public E toEntity(RequestDto dto) throws MapperException {
        E entity = mapper.toEntity(dto);
        toBaseEntity(dto, entity);
        return entity;
    }

    public ResponseDto toDto(E entity) throws MapperException {
        ResponseDto dto = mapper.toDto(entity);
        toBaseDto(entity, dto);
        return dto;
    }
}
