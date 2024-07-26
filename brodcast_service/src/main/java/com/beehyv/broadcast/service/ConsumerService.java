package com.beehyv.broadcast.service;

import com.beehyv.broadcast.dto.fortificationResponseDto.BatchResponseDto;
import com.beehyv.broadcast.dto.fortificationResponseDto.LotResponseDto;
import com.beehyv.broadcast.dto.labResponseDto.LabSampleResponseDto;
import com.beehyv.broadcast.dto.requestDto.EventUpdate;
import com.beehyv.broadcast.dto.responseDto.EventNotificationResponseDto;
import com.beehyv.broadcast.enums.StateType;
import com.beehyv.broadcast.helper.Constants;
import com.beehyv.broadcast.manager.EventLogManager;
import com.beehyv.broadcast.manager.EventManager;
import com.beehyv.broadcast.manager.SubscriberManager;
import com.beehyv.broadcast.model.Event;
import com.beehyv.broadcast.model.EventLog;
import com.beehyv.broadcast.model.Subscriber;
import com.beehyv.broadcast.utils.HttpUtils;
import com.beehyv.parent.exceptions.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private static final String algorithm = "AES/CBC/PKCS5PADDING";
    private static final Logger logger = Logger.getLogger(ConsumerService.class);
    private final EventManager eventManager;
    private final EventLogManager eventLogManager;
    private final SubscriberManager subscriberManager;

    @KafkaListener(topics = "event-update", groupId = "myGroup")
    public void consumeMessage(String message) {
        consumeMessage(message, 0, null);
    }

    public void consumeMessage(String message, Integer retryCount, Long eventLongId) {
        log.info("Message received: {{}}", message);
        EventNotificationResponseDto body;
        String data;
        String response;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            EventUpdate eventUpdate = mapper.readValue(message, EventUpdate.class);
            Event event = eventManager.findByName(eventUpdate.getStateName());
            List<Subscriber> subscribers = subscriberManager.findByGeoId(eventUpdate.getStateGeoId());
            Long batchId;
            Long lotId;
            if (event.getStateType().equals(StateType.BATCH)) {
                batchId = eventUpdate.getEntityId();
                BatchResponseDto batch = HttpUtils.callGetAPI(Constants.batchDetails + batchId, BatchResponseDto.class);
                List<LabSampleResponseDto> labSamples = HttpUtils.callGetAPI(Constants.batchLabSampleDetails + batchId, List.class);
                batch.setLabSampleDetails(new HashSet<>(labSamples));
                data = mapper.writeValueAsString(batch);
            } else {
                lotId = eventUpdate.getEntityId();
                LotResponseDto lot = HttpUtils.callGetAPI(Constants.lotDetails + lotId, LotResponseDto.class);
                List<LabSampleResponseDto> lotLabSamples = HttpUtils.callGetAPI(Constants.lotLabSampleDetails + lotId, List.class);
                lot.setLotLabSampleDetails(new HashSet<>(lotLabSamples));
                if (lot.getBatch() != null) {
                    batchId = lot.getBatch().getId();
                    List<LabSampleResponseDto> batchLabSamples = HttpUtils.callGetAPI(Constants.batchLabSampleDetails + batchId, List.class);
                    lot.getBatch().setLabSampleDetails(new HashSet<>(batchLabSamples));
                }
                data = mapper.writeValueAsString(lot);
            }
            body = EventNotificationResponseDto
                    .builder()
                    .eventName(eventUpdate.getStateName())
                    .data(data)
                    .build();
            response = mapper.writeValueAsString(body);
            subscribers.forEach(s -> {
                String clientId = s.getClientId();
                String clientSecret = s.getClientSecret();
                IvParameterSpec iv = new IvParameterSpec(clientId.getBytes(StandardCharsets.UTF_8));
                SecretKeySpec keySpec = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "AES");

                Cipher cipher = null;
                try {
                    cipher = Cipher.getInstance(algorithm);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

                    byte[] encrypted = cipher.doFinal(response.getBytes());
                    String encodedResponse = Base64.encodeBase64String(encrypted);
                    HttpUtils.callPostApi(s.getUrl(), encodedResponse);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                         InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                    throw new RuntimeException(e);
                } catch (CustomException e) {
                    throw new CustomException();
                }

                if (eventLongId != null) {
                    EventLog eventLog = eventLogManager.findById(eventLongId);
                    eventLog.setIsRetry(false);
                    eventLogManager.update(eventLog);
                }

            });
        } catch (JsonProcessingException e) {
            log.error("Json Processing exception occurred for event : {{}}", message);
            //save in db for later purpose
        } catch (RuntimeException e) {
            log.error("error with encryption");
        } catch (Exception e) {
            if (retryCount < Constants.retryCount) {
                consumeMessage(message, retryCount + 1, eventLongId);
            } else {
                EventLog eventLog = eventLogManager.getEventLogByEvent(message, "fortification")
                        .orElse(new EventLog());
                eventLog.setEventString(message);
                eventLog.setErrorMessage(e.getLocalizedMessage());
                eventLog.setServiceName("fortification");
                eventLog.setIsRetry(true);
                eventLogManager.saveEventLog(eventLog);
                log.error("Error occurred while sending event update message: " + e.getMessage());
            }
            log.error("Exception occurred at : {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "batch-update-new", groupId = "myGroup")
    public void batchUpdate(String message) {
        consumeMessage(message, 0, null);
    }

    public void log(String message) {
        log.info("Message received: {{}}", message);
        EventNotificationResponseDto body;
        String data;
        String response;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            EventUpdate eventUpdate = mapper.readValue(message, EventUpdate.class);
            Event event = eventManager.findByName(eventUpdate.getStateName());
            List<Subscriber> subscribers = subscriberManager.findByGeoId(eventUpdate.getStateGeoId());
            Long batchId;
            Long lotId;
            if (event.getStateType().equals(StateType.BATCH)) {
                batchId = eventUpdate.getEntityId();
                BatchResponseDto batch = HttpUtils.callGetAPI(Constants.batchDetails + batchId, BatchResponseDto.class);
                List<LabSampleResponseDto> labSamples = HttpUtils.callGetAPI(Constants.batchLabSampleDetails + batchId, List.class);
                batch.setLabSampleDetails(new HashSet<>(labSamples));
                data = mapper.writeValueAsString(batch);
            } else {
                lotId = eventUpdate.getEntityId();
                LotResponseDto lot = HttpUtils.callGetAPI(Constants.lotDetails + lotId, LotResponseDto.class);
                List<LabSampleResponseDto> lotLabSamples = HttpUtils.callGetAPI(Constants.lotLabSampleDetails + lotId, List.class);
                lot.setLotLabSampleDetails(new HashSet<>(lotLabSamples));
                if (lot.getBatch() != null) {
                    batchId = lot.getBatch().getId();
                    List<LabSampleResponseDto> batchLabSamples = HttpUtils.callGetAPI(Constants.batchLabSampleDetails + batchId, List.class);
                    lot.getBatch().setLabSampleDetails(new HashSet<>(batchLabSamples));
                }
                data = mapper.writeValueAsString(lot);
            }
            body = EventNotificationResponseDto
                    .builder()
                    .eventName(eventUpdate.getStateName())
                    .data(data)
                    .build();
            response = mapper.writeValueAsString(body);
            subscribers.forEach(s -> {
                String clientId = s.getClientId();
                String clientSecret = s.getClientSecret();
                try {
                    IvParameterSpec iv = new IvParameterSpec(clientId.getBytes(StandardCharsets.UTF_8));
                    SecretKeySpec keySpec = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "AES");

                    Cipher cipher = Cipher.getInstance(algorithm);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

                    byte[] encrypted = cipher.doFinal(response.getBytes());
                    String encodedResponse = Base64.encodeBase64String(encrypted);
                    HttpUtils.callPostApi(s.getUrl(), encodedResponse);
                } catch (CustomException e) {

                } catch (Exception ex) {
                    //log in db for future use
                    log.error("Exception occurred at encrypting data: {}", ex.getMessage());
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Json Processing exception occurred for event : {{}}", message);
            //save in db for later purpose
        }


    }
}
