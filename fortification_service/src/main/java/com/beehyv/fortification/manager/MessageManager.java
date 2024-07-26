package com.beehyv.fortification.manager;

import com.beehyv.fortification.dto.iam.AddressResponseDto;
import com.beehyv.fortification.dto.requestDto.*;
import com.beehyv.fortification.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.fortification.dto.responseDto.LabNameAddressResponseDto;
import com.beehyv.fortification.dto.responseDto.LocationResponseDto;
import com.beehyv.fortification.entity.BatchEventEntity;
import com.beehyv.fortification.entity.Lot;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.ActionType;
import com.beehyv.fortification.enums.EntityType;
import com.beehyv.fortification.enums.SampleTestResult;
import com.beehyv.fortification.helper.Constants;
import com.beehyv.fortification.helper.IamServiceRestHelper;
import com.beehyv.fortification.helper.LabServiceManagementHelper;
import com.beehyv.fortification.service.impl.LotServiceImpl;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class MessageManager {
    private final KafkaTemplate<String, String> template;
    @Autowired
    private KeycloakInfo keycloakInfo;

    private ObjectMapper mapper = new ObjectMapper();

    @Async
    public void statusChangeHandler(EntityStateRequestDTO dto, State state, Map userInfo, Long manufacturerId, String token, Boolean isFlowSkipped) {
        statusChangeHandlerSynchronously(dto, state, userInfo, manufacturerId, token, isFlowSkipped);
    }

    public void statusChangeHandlerSynchronously(EntityStateRequestDTO dto, State state, Map userInfo, Long manufacturerId, String token, Boolean isFlowSkipped) {
        BatchEventEntity entity = new BatchEventEntity();
        EventUpdate eventUpdate = new EventUpdate();
        boolean isSuperAdmin = this.isSuperAdmin(userInfo);
        try {
            if (state != null) {
                entity.setState(state.getDisplayName());
                StateType type = state.getType();
                entity.setType(type.name());
                eventUpdate.setStateName(state.getName());
                long entityId = 0L;
                if (type.equals(StateType.LOT) && dto.getLotId() != null) {
                    entityId = dto.getLotId();
                    eventUpdate.setEntity(EntityType.lot);
                    eventUpdate.setEntityId(entityId);
                } else if (type.equals(StateType.BATCH) && dto.getBatchId() != null) {
                    entityId = dto.getBatchId();
                    eventUpdate.setEntity(EntityType.batch);
                    eventUpdate.setEntityId(entityId);
                }
                entity.setEntityId(Long.toString(entityId));
            }
            entity.setComments(dto.getComments());
            entity.setCreatedBy((String) userInfo.get("email"));
            if (!isSuperAdmin && !isFlowSkipped) {
                entity.setManufacturerName((String) userInfo.get("manufacturerName"));
                entity.setManufacturerAddress(manufacturerAddress((String) userInfo.get("manufacturerAddress")));
                String url =Constants.IAM_SERVICE_URL + "manufacturer/licenseNumber/"+((String) userInfo.get("manufacturerName"));
                String licenseNumber = IamServiceRestHelper.fetchResponse(url, String.class,token);
                entity.setLicenseNumber(licenseNumber);
                eventUpdate.setStateGeoId(Integer.parseInt(getAddressResponseDtoFromString((String) userInfo.get("manufacturerAddress")).getState().getGeoId()));
            } else {
                try {
                    if (state != null && state.getActionType().equals(ActionType.lab)) {
                        LabNameAddressResponseDto response;
                        if (dto.getLabId() != null && dto.getLabId() != 0L) {
                            String url = Constants.LAB_SERVICE_URL + "address/" + dto.getLabId() + "/lab-address";
                            UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                                    .queryParam("labIds", Collections.singletonList(dto.getLabId()))
                                    .build();
                            response = LabServiceManagementHelper.fetchLabNameAddress(builder.toUriString(), token);
                        } else {
                            EntityType entityType;
                            Long entityId;
                            if (dto.getBatchId() != null) {
                                entityType = EntityType.batch;
                                entityId = dto.getBatchId();
                            } else {
                                entityType = EntityType.lot;
                                entityId = dto.getLotId();
                            }
                            String url = Constants.LAB_SERVICE_URL + "0/sample/" + entityType + "/address/" + entityId;
                            response = LabServiceManagementHelper.fetchLabNameAddress(url, token);
                        }
                        entity.setManufacturerAddress(response.getCompleteAddress());
                        entity.setManufacturerName(response.getName());
                        entity.setLicenseNumber(response.getLabCertificateNumber());
                        eventUpdate.setStateGeoId(Integer.parseInt(getAddressResponseDtoFromString(response.getCompleteAddress()).getState().getGeoId()));
                    } else {
                        Map<String, Map<String, String>> nameAddressMap = IamServiceRestHelper.getNameAndAddress(Collections.singletonList(manufacturerId), token);
                        Map<String, String> map = nameAddressMap.getOrDefault(manufacturerId.toString(), null);
                        entity.setManufacturerName(map.get("name"));
                        entity.setManufacturerAddress(manufacturerAddress(map.get("address")));
                        entity.setLicenseNumber(map.get("licenseNo"));
                        eventUpdate.setStateGeoId(Integer.parseInt(getAddressResponseDtoFromString(map.get("address")).getState().getGeoId()));
                    }
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
            if (dto.getDateOfAction() != null) {
                entity.setDateOfAction(new Timestamp(dto.getDateOfAction().getTime()));
            }
            ObjectMapper mapper = new ObjectMapper();
            String valueMap = mapper.writeValueAsString(entity);
            sendMessage(valueMap, "batch-update-new");
            try {
                String eventUpdateString = mapper.writeValueAsString(eventUpdate);
                sendMessage(eventUpdateString, "event-update");
            } catch (Exception e) {
                //TODO: think to handle exception later
            }
        } catch (Exception e) {
            log.error("Log Service Error: {{}}", e.getMessage(), e);
            log.debug(String.valueOf(entity));
            log.debug(String.valueOf(dto));
        }
    }

    @Async
    public void statusChangeHandlerForMaterial(EntityStateRequestDTO dto, State state, Map userInfo, String token, Boolean isFlowSkipped, Set<BatchPropertyRequestDto> batchProperties){
        BatchEventEntity entity = new BatchEventEntity();
        boolean isSuperAdmin = this.isSuperAdmin(userInfo);
        EventUpdate eventUpdate = new EventUpdate();
        try {
            if (state != null) {
                entity.setState(state.getDisplayName());
                StateType type = state.getType();
                entity.setType(type.name());
                long entityId = 0L;
                if (type.equals(StateType.LOT) && dto.getLotId() != null) {
                    entityId = dto.getLotId();
                    eventUpdate.setEntity(EntityType.lot);
                    eventUpdate.setEntityId(dto.getLotId());
                } else if (type.equals(StateType.BATCH) && dto.getBatchId() != null) {
                    entityId = dto.getBatchId();
                    eventUpdate.setEntity(EntityType.batch);
                    eventUpdate.setEntityId(dto.getBatchId());
                }
                entity.setEntityId(Long.toString(entityId));
            }
            entity.setComments(dto.getComments());
            entity.setCreatedBy((String) userInfo.get("email"));
            if (!isFlowSkipped) {
                batchProperties.stream().filter(batchProperty -> batchProperty.getName().equals("manufacture_name"))
                        .findFirst().ifPresent(batchProperty -> entity.setManufacturerName(batchProperty.getValue()));
                batchProperties.stream().filter(batchProperty -> batchProperty.getName().equals("manufacture_completeAddress"))
                        .findFirst().ifPresent(batchProperty -> entity.setManufacturerAddress(batchProperty.getValue()));
                String url = Constants.IAM_SERVICE_URL + "manufacturer/licenseNumber/" + entity.getManufacturerName();
                String licenseNumber = IamServiceRestHelper.fetchResponse(url, String.class, token);
                entity.setLicenseNumber(licenseNumber);
                if (dto.getDateOfAction() != null) {
                    entity.setDateOfAction(new Timestamp(dto.getDateOfAction().getTime()));
                }
                eventUpdate.setStateGeoId(Integer.parseInt(getAddressResponseDtoFromString((String) userInfo.get("manufacturerAddress")).getState().getGeoId()));
                String valueMap = mapper.writeValueAsString(entity);
                sendMessage(valueMap, "batch-update-new");
                try {
                    String eventUpdateString = mapper.writeValueAsString(eventUpdate);
                    sendMessage(eventUpdateString, "event-update");
                } catch (Exception e) {
                }
            }
        }catch (Exception e) {
            log.error("Log Service Error: {{}}", e.getMessage(), e);
            log.debug(String.valueOf(entity));
            log.debug(String.valueOf(dto));
        }
    }

    private boolean isSuperAdmin(Map userInfo) {
        Set<String> userRoles = (Set<String>) userInfo.get("roles");
        return userRoles.stream().anyMatch(r -> r.contains("SUPERADMIN"));
    }

    public String manufacturerAddress(String request) {
        AddressLocationResponseDto addressDto = new AddressLocationResponseDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String address = null;
        try {
            addressDto = getAddressResponseDtoFromString(request);
            Map<String, Object> map = new HashMap<>();
            if (addressDto != null) {
                map.put("laneOne", addressDto.getLaneOne() != null ? addressDto.getLaneOne() : "");
                map.put("laneTwo", addressDto.getLaneTwo() != null ? addressDto.getLaneTwo() : "");
                map.put("village", addressDto.getVillage().getName() != null ? addressDto.getVillage().getName() : "");
                map.put("district", addressDto.getDistrict().getName() != null ? addressDto.getDistrict().getName() : "");
                map.put("state", addressDto.getState() != null ? addressDto.getState().getName() : "");
                map.put("country", addressDto.getCountry() != null ? addressDto.getCountry().getName() : "");
                map.put("latitude", addressDto.getLatitude() != null ? addressDto.getLatitude() : "");
                map.put("longitude", addressDto.getLatitude() != null ? addressDto.getLongitude() : "");
                map.put("pincode", addressDto.getPinCode() != null ? addressDto.getPinCode() : "");
            }
            address = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return address;
    }

    public AddressLocationResponseDto getAddressResponseDtoFromString(String request) {
        AddressLocationResponseDto addressDto = new AddressLocationResponseDto();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            addressDto = objectMapper.readValue(request, AddressLocationResponseDto.class);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return addressDto;
    }

    @SneakyThrows
    public <T> void sendMessage(T entity, String topic) {
        Message<T> message = MessageBuilder
                .withPayload(entity)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        template.send(message);
        log.info(String.format("Message sent: %s", message));
    }

    @Async
    public void sendBatchUpdate(Long manufacturerId, Long categoryId, Double quantityDifference,
                                State state, Map<String, Object> userInfo, String token,
                                Date date, SampleTestResult testResult, Boolean isBatchTested, Long batchId) {
        ObjectMapper objectMapper = new ObjectMapper();
        EventUpdate eventUpdate = new EventUpdate();
        eventUpdate.setEntity(EntityType.batch);
        eventUpdate.setEntityId(batchId);
        eventUpdate.setStateName(state.getName());
        try {
            Object labId = userInfo.get("labId");
            AddressLocationResponseDto address = null;
            if (Objects.isNull(labId) || userInfo.get("manufacturerAddress") == null) {
                String url = Constants.IAM_SERVICE_URL + "address/manufacturer/" + manufacturerId;
                AddressResponseDto address1 = IamServiceRestHelper.fetchResponse(url, AddressResponseDto.class, token);
                address = new AddressLocationResponseDto();
                BeanUtils.copyProperties(address1, address);
                if (address1 != null && address1.getVillage() != null && address1.getVillage().getDistrict() != null) {
                    LocationResponseDto district = new LocationResponseDto();
                    BeanUtils.copyProperties(address1.getVillage().getDistrict(), district);
                    address.setDistrict(district);
                    if (address1.getVillage().getDistrict().getState() != null) {
                        LocationResponseDto state1 = new LocationResponseDto();
                        BeanUtils.copyProperties(address1.getVillage().getDistrict().getState(), state1);
                        address.setState(state1);
                        if (address1.getVillage().getDistrict().getState().getCountry() != null) {
                            LocationResponseDto country = new LocationResponseDto();
                            BeanUtils.copyProperties(address1.getVillage().getDistrict().getState().getCountry(), country);
                            address.setCountry(country);
                        }
                    }
                }
            } else {
                address = objectMapper.readValue((String) userInfo.get("manufacturerAddress"), AddressLocationResponseDto.class);
            }
            BatchStateEvent event = BatchStateEvent.builder()
                    .categoryId(categoryId)
                    .quantity(quantityDifference)
                    .state(state.getName())
                    .manufacturerId(manufacturerId)
                    .countryGeoId(address != null ? address.getCountry().getGeoId() : null)
                    .stateGeoId(address != null ? address.getState().getGeoId() : null)
                    .districtGeoId(address != null ? address.getDistrict().getGeoId() : null)
                    .pincode(address != null ? address.getPinCode() : null)
                    .date(date)
                    .testResult(testResult)
                    .isBatchTested(isBatchTested)
                    .build();
            this.sendMessage(event, "batch-state-geo-update");
            eventUpdate.setStateGeoId((address != null ? Integer.valueOf(address.getState().getGeoId()) : null));
            try {
                String eventUpdateString = mapper.writeValueAsString(eventUpdate);
                sendMessage(eventUpdateString, "event-update");
            } catch (Exception e) {
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendLotUpdate(Long targetManufacturerId, Long batchCategoryId, Long batchManufacturerId, Double quantityDifference,
                              String stateName, Date date, SampleTestResult testResult, String token, Boolean isBatchTested, Long lotId) {
        try {
            EventUpdate eventUpdate = new EventUpdate();
            eventUpdate.setEntity(EntityType.lot);
            eventUpdate.setEntityId(lotId);
            eventUpdate.setStateName(stateName);
            String url = Constants.IAM_SERVICE_URL + "address/manufacturer/" + targetManufacturerId;
            AddressResponseDto address1 = IamServiceRestHelper.fetchResponse(url, AddressResponseDto.class, token);
            AddressLocationResponseDto address = new AddressLocationResponseDto();
            BeanUtils.copyProperties(address1, address);
            if (address1 != null && address1.getVillage() != null && address1.getVillage().getDistrict() != null) {
                LocationResponseDto district = new LocationResponseDto();
                BeanUtils.copyProperties(address1.getVillage().getDistrict(), district);
                address.setDistrict(district);
                if (address1.getVillage().getDistrict().getState() != null) {
                    LocationResponseDto state1 = new LocationResponseDto();
                    BeanUtils.copyProperties(address1.getVillage().getDistrict().getState(), state1);
                    address.setState(state1);
                    if (address1.getVillage().getDistrict().getState().getCountry() != null) {
                        LocationResponseDto country = new LocationResponseDto();
                        BeanUtils.copyProperties(address1.getVillage().getDistrict().getState().getCountry(), country);
                        address.setCountry(country);
                    }
                }
            }
            LotStateEvent event = LotStateEvent
                    .builder()
                    .categoryId(batchCategoryId)
                    .targetManufacturerId(targetManufacturerId)
                    .batchManufacturerId(batchManufacturerId)
                    .state(stateName)
                    .countryGeoId(address.getCountry().getGeoId())
                    .stateGeoId(address.getState().getGeoId())
                    .districtGeoId(address.getDistrict().getGeoId())
                    .quantity(quantityDifference)
                    .isBatchTested(isBatchTested)
                    .date(date)
                    .testResult(testResult)
                    .lotId(lotId)
                    .build();
            this.sendMessage(event, "lot-state-geo-update");
            try {
                String eventUpdateString = mapper.writeValueAsString(eventUpdate);
                sendMessage(eventUpdateString, "event-update");
            } catch (Exception e) {
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendNotification(FirebaseEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String message;
        try {
            message = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            log.info("Error occurred while serializing the firebase event to string: " + e.getMessage());
            return;
        }
        this.sendMessage(message, "fire-base-notification");
    }

    @Async
    public void sendMessage(List<State> states, Lot lot, SampleTestResult sampleTestResult, EntityStateRequestDTO entityStateRequestDTO, Map userInfo, String token) {
        states.forEach(s -> {
            sendLotUpdate(
                    lot.getTargetManufacturerId(),
                    lot.getCategory().getId(),
                    lot.getManufacturerId(),
                    lot.getTotalQuantity() * lot.getUom().getConversionFactorKg(),
                    s.getName(),
                    lot.getDateOfDispatch(),
                    sampleTestResult,
                    token,
                    lot.getBatch().getIsLabTested(),
                    lot.getId()
            );
            statusChangeHandlerSynchronously(
                    entityStateRequestDTO,
                    s,
                    userInfo,
                    LotServiceImpl.stateMap.get(s.getName()).equals("source") ? lot.getManufacturerId() : lot.getTargetManufacturerId(),
                    token,
                    true
            );
        });
    }

    @Async
    public void sendStatusHandlerMessage(Map<State, Boolean> stateMap, EntityStateRequestDTO entityStateRequestDTO, Map userInfo, Lot lot, String token) {
        for (Map.Entry<State, Boolean> entry : stateMap.entrySet()) {
            State state = entry.getKey();
            Boolean isFlowSkipped = entry.getValue();
            statusChangeHandler(
                    entityStateRequestDTO,
                    state,
                    userInfo,
                    LotServiceImpl.stateMap.get(state.getName()).equals("source") ? lot.getManufacturerId() : lot.getTargetManufacturerId(),
                    token,
                    isFlowSkipped
            );
        }
    }
}
