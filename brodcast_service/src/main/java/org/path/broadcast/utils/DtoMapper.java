package org.path.broadcast.utils;

import org.path.broadcast.dto.responseDto.EventResponseDto;
import org.path.broadcast.dto.responseDto.SubscriberResponseDto;
import org.path.broadcast.model.Event;
import org.path.broadcast.model.Subscriber;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {UUID.class})
public interface DtoMapper {

    EventResponseDto mapToDto(Event entity);

    SubscriberResponseDto mapToDto(Subscriber subscriber);
}
