package com.beehyv.Immudb.mapper;

import com.beehyv.Immudb.dto.FirebaseEvent;
import com.beehyv.Immudb.dto.NotificationResponseDto;
import com.beehyv.Immudb.entity.NotificationEvent;

public class NotificationMapper {

  public static NotificationResponseDto mapNotificationEntityToDto(NotificationEvent event){
    NotificationResponseDto dto = new NotificationResponseDto();
    dto.setCurrentStateDisplayName(event.getCurrentStateDisplayName());
    dto.setNotificationTitle(event.getNotificationTitle());
    dto.setEntityType(event.getEntityType());
    dto.setEntityId(event.getEntityId());
    dto.setCategoryName(event.getCategoryName());
    dto.setCategoryId(event.getCategoryId());
    dto.setEntityNo(event.getEntityNo());
    dto.setPreviousStateDisplayName(event.getPreviousStateDisplayName());
    dto.setIsIndependentBatch(event.getIsIndependentBatch());
    dto.setNotificationDate(event.getNotificationDate());
    dto.setLabSampleId(event.getLabSampleId());
    return dto;
  }

  public static NotificationEvent mapNotificationDtoToEntity(FirebaseEvent dto, String notificationTitle, Boolean isTargetManufacturer){
    NotificationEvent entity = new NotificationEvent();
    entity.setEntityType(dto.getEntity());
    entity.setEntityId(dto.getId());
    entity.setEntityNo(dto.getEntityNo());
    entity.setNotificationDate(dto.getNotificationDate());
    entity.setManufacturerId(dto.getManufacturerId());
    entity.setTargetManufacturerId(dto.getTargetManufacturerId());
    entity.setCategoryId(dto.getCategoryId());
    entity.setCategoryName(dto.getCategoryName());
    entity.setCurrentStateName(dto.getCurrentStateName());
    entity.setDateOfAction(dto.getDateOfAction());
    entity.setLabId(dto.getLabId() != null ? Long.parseLong(dto.getLabId()) : 0L);
    entity.setNotificationTitle(notificationTitle);
    entity.setIsIndependentBatch(dto.getIsIndependentBatch());
    entity.setPreviousStateName(dto.getPreviousStateName());
    entity.setIsTargetManufacturer(isTargetManufacturer);
    entity.setCurrentStateDisplayName(dto.getCurrentStateDisplayName());
    entity.setPreviousStateDisplayName(dto.getPreviousStateDisplayName());
    entity.setLabSampleId(dto.getLabSampleId());
    return entity;
  }
}
