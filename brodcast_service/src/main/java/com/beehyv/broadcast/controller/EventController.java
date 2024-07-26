package com.beehyv.broadcast.controller;

import com.beehyv.broadcast.dto.responseDto.EventResponseDto;
import com.beehyv.broadcast.dto.responseDto.ListResponse;
import com.beehyv.broadcast.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@RequiredArgsConstructor
@Tag(name = "Event Controller")
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/getAll")
    public ResponseEntity<ListResponse<EventResponseDto>> getAll() {
        return new ResponseEntity<>(eventService.getAllEvents(1, 1), HttpStatus.CREATED);
    }

    @GetMapping("/getById")
    public ResponseEntity<EventResponseDto> getAllById(@QueryParam("id") Long id) {
        return new ResponseEntity<>(eventService.findById(id), HttpStatus.CREATED);
    }
}
