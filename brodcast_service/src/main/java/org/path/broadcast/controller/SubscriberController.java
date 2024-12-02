package org.path.broadcast.controller;

import org.path.broadcast.dto.requestDto.SubscriberRequestDto;
import org.path.broadcast.dto.responseDto.SubscriberCredentialsResponseDto;
import org.path.broadcast.service.SubscriberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Subscriber Controller")
@RequestMapping("/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<SubscriberCredentialsResponseDto> createSubscriber(@RequestBody SubscriberRequestDto dto) {
        return new ResponseEntity<>(subscriberService.createSubscriber(dto), HttpStatus.CREATED);
    }

}
