package org.path.lab.manager;

import org.path.lab.dto.requestDto.SampleGeoStateEvent;
import org.path.lab.dto.responseDto.AddressResponseDTO;
import org.path.lab.entity.SampleState;
import org.path.lab.enums.LabSampleActionType;
import org.path.lab.helper.Constants;
import org.path.lab.helper.RestHelper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MessageManager {
    private final KafkaTemplate<String, String> template;
    private final RestHelper restHelper;

    @Async
    public void send(Long manufacturerId, Long categoryId, Long labId,
                     LabSampleActionType sampleAction, SampleState state, String token, Integer year){
        String url = Constants.IAM_BASE_URL+ "address/manufacturer/" + manufacturerId;
        try {
            AddressResponseDTO address = restHelper.fetchResponse(url, AddressResponseDTO.class, token);
            SampleGeoStateEvent event = new SampleGeoStateEvent();
            event.setCategoryId(categoryId);
            event.setManufacturerId(manufacturerId);
            event.setLabId(labId);
            event.setSampleSentYear(year);
            event.setState(state.getName());
            event.setAction(sampleAction.name());
            if (address != null) {
                event.setPincode(address.getPinCode());
            }
            if(address != null && address.getVillage() != null && address.getVillage().getDistrict() != null) {
                event.setDistrictGeoId(address.getVillage().getDistrict().getGeoId());
                if(address.getVillage().getDistrict().getState() != null) {
                    event.setStateGeoId(address.getVillage().getDistrict().getState().getGeoId());
                    if(address.getVillage().getDistrict().getState().getCountry() != null) {
                        event.setCountryGeoId(address.getVillage().getDistrict().getState().getCountry().getGeoId());
                    }
                }
            }
            this.sendMessage(event, "sample-state-count-update");
        } catch (Exception e) {
            log.info("Error while sending update {{}}", e.getMessage());
        }
    }

    @SneakyThrows
    public <T> void sendMessage(T entity, String topic){
        Message<T> message = MessageBuilder
                .withPayload(entity)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.GROUP_ID, "lab-events-group")
                .build();
        template.send(message);
        log.info("Message sent to topic {{}}: {{}}", topic, message);
    }
}
