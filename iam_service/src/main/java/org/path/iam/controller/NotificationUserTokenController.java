package org.path.iam.controller;

import org.path.iam.dto.requestDto.NotificationUserTokenRequestDto;
import org.path.iam.service.NotificationUserTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration/token")
@Tag(name = "Registration Token Controller")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class NotificationUserTokenController {
    private final NotificationUserTokenService notificationUserTokenService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody NotificationUserTokenRequestDto dto){
        return new ResponseEntity<>( notificationUserTokenService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getRegistrationTokens(@RequestParam Long categoryId,
                                                @RequestParam Long manufacturerId,
                                                @RequestParam(required = false) Long labId,
                                                @RequestParam(required = false) Long targetManufacturerId,
                                                @RequestParam String state){
        return new ResponseEntity<>(
            notificationUserTokenService.getRegistrationTokens(categoryId, manufacturerId, labId, targetManufacturerId, state), HttpStatus.OK);
    }

    @DeleteMapping("/{registrationToken}")
    public ResponseEntity<?> deleteManufacturerDocById(@PathVariable("registrationToken") String registrationToken){
        notificationUserTokenService.deleteRegistrationToken(registrationToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
