package org.path.Immudb.controller;

import org.path.Immudb.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Notification Controller")
@RequestMapping("/notification")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class NotificationController {

  @Autowired
  private NotificationService service;

  @GetMapping("/count")
  public ResponseEntity<?> getNotificationCount() {
    return ResponseEntity.ok(service.getNotificationCount());
  }

  @GetMapping("/list")
  public ResponseEntity<?> getNotifications(
      @RequestParam(required = false) Integer pageNumber,
      @RequestParam(required = false) Integer pageSize
  ){
    return ResponseEntity.ok(service.getNotifications(pageNumber, pageSize));
  }

}
