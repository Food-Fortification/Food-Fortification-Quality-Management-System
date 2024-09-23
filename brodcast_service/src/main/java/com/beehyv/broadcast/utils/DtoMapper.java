package com.beehyv.broadcast.utils;

import com.beehyv.broadcast.dto.responseDto.EventResponseDto;
import com.beehyv.broadcast.dto.responseDto.SubscriberResponseDto;
import com.beehyv.broadcast.model.Event;
import com.beehyv.broadcast.model.Subscriber;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {UUID.class})
public interface DtoMapper {

    EventResponseDto mapToDto(Event entity);

    SubscriberResponseDto mapToDto(Subscriber subscriber);
}
