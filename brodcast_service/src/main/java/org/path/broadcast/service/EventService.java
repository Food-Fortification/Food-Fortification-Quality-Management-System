package org.path.broadcast.service;

import org.path.broadcast.dto.responseDto.EventResponseDto;
import org.path.broadcast.dto.responseDto.ListResponse;
import org.path.broadcast.manager.EventManager;
import org.path.broadcast.model.Event;
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
