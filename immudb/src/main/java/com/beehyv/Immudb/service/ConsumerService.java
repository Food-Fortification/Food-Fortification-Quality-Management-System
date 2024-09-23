package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.FirebaseEvent;
import com.beehyv.Immudb.entity.BatchEventEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
@AllArgsConstructor
public class ConsumerService {

    private final ImmudbService immudbService;

    private final NotificationService notificationService;

    @KafkaListener(topics = "batch-update-new", groupId = "myGroup")
    public void consumeMessage(String message) throws SQLException, JsonProcessingException {
        log.info("Message received: {{}}", message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            BatchEventEntity batchEvent = mapper.readValue(message,BatchEventEntity.class);
            immudbService.put(batchEvent, BatchEventEntity.class);
            log.info(" Completed insertion");
        } catch (Exception e) {
            log.info("Insertion failed: {{}}", e.getMessage());
            throw e;
        }

    }

    @KafkaListener(topics = "fire-base-notification", groupId = "myGroup")
    public void consumeNotification(String message) throws JsonProcessingException {
        log.info(String.format("Message received: %s", message));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        FirebaseEvent firebaseEvent = mapper.readValue(message, FirebaseEvent.class);
        notificationService.sendNotification(firebaseEvent);
    }

}
