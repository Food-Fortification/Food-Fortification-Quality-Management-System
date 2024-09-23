package com.beehyv.broadcast.service;

import com.beehyv.broadcast.dto.responseDto.EventResponseDto;
import com.beehyv.broadcast.dto.responseDto.ListResponse;
import com.beehyv.broadcast.manager.EventManager;
import com.beehyv.broadcast.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventManager eventManager;

    public ListResponse<EventResponseDto> getAllEvents(Integer pageNumber, Integer pageSize) {
        List<Event> entities = eventManager.findAll(pageNumber, pageSize);
        List<EventResponseDto> data = entities.stream().map(e -> new EventResponseDto(e.getId(), e.getDisplayName(), e.getStateName())).toList();
        return new ListResponse<>((long) data.size(), data);
    }

    public EventResponseDto findByName(String eventName) {
        Event entity = eventManager.findByName(eventName);
        if (entity != null) return new EventResponseDto(entity.getId(), entity.getDisplayName(), entity.getStateName());
        return null;
    }

    public EventResponseDto findById(Long id) {
        Event entity = eventManager.findById(id);
        if (entity != null) return new EventResponseDto(entity.getId(), entity.getDisplayName(), entity.getStateName());
        return null;
    }
}
