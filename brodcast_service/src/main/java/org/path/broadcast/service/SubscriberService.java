package org.path.broadcast.service;

import org.path.broadcast.dto.requestDto.SubscriberRequestDto;
import org.path.broadcast.dto.responseDto.SubscriberCredentialsResponseDto;
import org.path.broadcast.dto.responseDto.SubscriberResponseDto;
import org.path.broadcast.manager.SubscriberManager;
import org.path.broadcast.model.Subscriber;
import org.path.broadcast.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberManager subscriberManager;
    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    public SubscriberCredentialsResponseDto createSubscriber(SubscriberRequestDto dto) {
        Subscriber entity = new Subscriber();
        entity.setName(dto.getName());
        entity.setStateGeoId(dto.getStateGeoId());
        entity.setClientId(RandomStringUtils.randomAlphanumeric(16));
        entity.setClientSecret(RandomStringUtils.randomAlphanumeric(32));
        entity.setUrl(dto.getUrl());
        entity = subscriberManager.create(entity);
        return SubscriberCredentialsResponseDto
                .builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .build();
    }

    public SubscriberCredentialsResponseDto getSubscriberCredentials(long id) {
        Subscriber entity = subscriberManager.findById(id);
        return SubscriberCredentialsResponseDto
                .builder()
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .build();
    }

    public void updateSubscriber(SubscriberRequestDto dto) {
        Subscriber existingSubscriber = subscriberManager.findById(dto.getId());
        existingSubscriber.setName(dto.getName());
        if (dto.getUrl() != null) existingSubscriber.setUrl(dto.getUrl());
        subscriberManager.update(existingSubscriber);
    }

    public SubscriberResponseDto findById(Long id) {
        Subscriber entity = subscriberManager.findById(id);
        return dtoMapper.mapToDto(entity);
    }

    public void deleteSubscriber(Long id) {
        subscriberManager.delete(id);
    }
}
