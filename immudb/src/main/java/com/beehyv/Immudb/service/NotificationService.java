package com.beehyv.Immudb.service;

import com.beehyv.Immudb.dto.*;
import com.beehyv.Immudb.entity.NotificationEvent;
import com.beehyv.Immudb.manager.NotificationEventManager;
import com.beehyv.Immudb.mapper.NotificationMapper;
import com.beehyv.Immudb.utils.HttpUtils;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

  private final FirebaseMessaging firebaseMessaging;
  @Autowired
  private NotificationEventManager manager;

  @Autowired
  private KeycloakInfo keycloakInfo;

  @Value("${service.iam.baseUrl}")
  private String iamBaseUrl ;

  @Value("${NOTIFICATION_IMAGE_URL: }")
  private String imageUrl;

  @Value("${firebase.android.channel: }")
  private String androidChannelId;

  private static final Map<String,String> previousCategoryMap = new HashMap<>();

  static {
    previousCategoryMap.put("FRK","PREMIX");
    previousCategoryMap.put("MILLER","FRK");
    previousCategoryMap.put("WAREHOUSE","MILLER");
  }

  public NotificationService(@Autowired(required = false) FirebaseMessaging firebaseMessaging, NotificationEventManager manager) {
    this.firebaseMessaging = firebaseMessaging;
    this.manager = manager;
  }

  public void sendNotification(FirebaseEvent firebaseEvent) throws JsonProcessingException {
    String notificationBody;
    String notificationTitle;
    String inAppNotificationTitle;

    boolean isTargetManufacturer = false;

    switch(firebaseEvent.getCurrentStateName()){
      case "sentBatchSampleToLabTest" -> {
        notificationBody ="Batch %s has been sent for lab test";
        notificationTitle = "Sample sent to lab";
        inAppNotificationTitle = "Batch %s has been moved from {%s} to {%s}";
      }
      case "batchSampleInLab" -> {
        notificationBody= "Batch %s has been received in Lab";
        notificationTitle = "Sample received";
        inAppNotificationTitle = "Batch %s has been moved from {%s} to {%s}";
      }
      case "batchSampleLabTestDone" -> {
        notificationBody ="Batch %s test has been done in lab";
        notificationTitle = "Test done";
        inAppNotificationTitle = "Batch %s has been moved from {%s} to {%s}";
      }
      case "rejected" -> {
        notificationBody = "Batch %s has been rejected after test has failed";
        notificationTitle = "Batch rejected";
        inAppNotificationTitle = "Batch %s has been moved from {%s} to {%s}";
      }
      case "batchToDispatch" -> {
        notificationBody ="Batch %s has been approved and ready to be dispatched";
        notificationTitle = "Batch approved";
        inAppNotificationTitle = "Batch %s has been moved from {%s} to {%s}";
      }
      case "created" -> {
        notificationBody= "Batch %s has been created";
        notificationTitle = "Batch created";
        inAppNotificationTitle = "Batch %s has been {%s}";
      }
      case "batchSampleRejected" -> {
        notificationBody = "Batch %s has been rejected by lab";
        notificationTitle = "Batch sample rejected";
        inAppNotificationTitle = "Batch %s has been moved from {%s} to {%s}";
      }
      case "lotSampleRejected" -> {
        notificationBody ="Lot %s has been  rejected";
        notificationTitle = "Lot sample rejected";
        isTargetManufacturer = true;
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "receivedRejected" -> {
        notificationBody = "Rejected lot %s has been received";
        isTargetManufacturer = true;
        notificationTitle = "Rejected lot received";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "sentBackRejected" -> {
        notificationBody = "Lot %s has been sent back after rejected";
        isTargetManufacturer = true;
        notificationTitle = "Lot sent back";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "toSendBackRejected" -> {
        notificationBody = "Lot %s test has been rejected and ready to send back";
        isTargetManufacturer = true;
        notificationTitle = "Lot to send back";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "approved" -> {
        notificationBody = "Lot %s has been approved";
        isTargetManufacturer = true;
        notificationTitle = "Lot approved";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "lotSampleLabTestDone" -> {
        notificationBody= "Lot %s test has been done";
        notificationTitle = "Test done";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "lotSampleInLab" -> {
        notificationBody= "Lot %s has been received in lab";
        notificationTitle = "Sample received";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "sentLotSampleToLabTest" -> {
        notificationBody= "Lot %s has been sent for lab test";
        isTargetManufacturer = true;
        notificationTitle = "Sample sent to lab";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "lotReceived" -> {
        notificationBody="Dispatched lot %s has been received";
        isTargetManufacturer = true;
        notificationTitle = "Lot received";
        inAppNotificationTitle = "Lot %s has been moved from {%s} to {%s}";
      }
      case "dispatched" -> {
        notificationBody= "Lot %s has been dispatched";
        isTargetManufacturer = true;
        notificationTitle = "Lot dispatched";
        inAppNotificationTitle = "Lot %s has been {%s}";
      }
      default -> {
        notificationBody = "  ";
        notificationTitle = "Rice Fortification";
        inAppNotificationTitle = "Sample %s has been moved";
      }
    }

    if(firebaseEvent.getCurrentStateName().equals("dispatched") || firebaseEvent.getCurrentStateName().equals("created")){
      inAppNotificationTitle = String.format(inAppNotificationTitle,firebaseEvent.getEntityNo(), firebaseEvent.getCurrentStateDisplayName());
    }else{
      inAppNotificationTitle = String.format(inAppNotificationTitle,firebaseEvent.getEntityNo(),firebaseEvent.getPreviousStateDisplayName(),firebaseEvent.getCurrentStateDisplayName());
    }

    List<String> previousLotStateList = List.of("lotSampleInLab", "lotSampleRejected", "lotSampleLabTestDone");
    if(previousLotStateList.contains(firebaseEvent.getCurrentStateName())){
      NotificationEvent event = manager.findByEntityIdAndState(firebaseEvent.getId(), "sentLotSampleToLabTest");
      firebaseEvent.setLabSampleId(event.getLabSampleId());
    }

    List<String> previousBatchStateList = List.of("batchSampleInLab", "batchSampleRejected", "batchSampleLabTestDone");
    if(previousBatchStateList.contains(firebaseEvent.getCurrentStateName())){
      NotificationEvent event = manager.findByEntityIdAndState(firebaseEvent.getId(), "sentBatchSampleToLabTest");
      firebaseEvent.setLabSampleId(event.getLabSampleId());
    }
    try{
      manager.create(NotificationMapper.mapNotificationDtoToEntity(firebaseEvent, inAppNotificationTitle, isTargetManufacturer));
    }catch (Exception e){
      log.info("Error occurred while saving notification to DB: " + e.getMessage());
    }

    Notification notification = Notification
        .builder()
        .setTitle(notificationTitle)
        .setBody(String.format(notificationBody,firebaseEvent.getEntityNo()))
        .setImage(imageUrl)
        .build();

    List<NotificationUserTokenResponseDto>  tokenDtoList = getTokens(firebaseEvent);
    Map<String, String> map = new HashMap<>();
    map.put("entityType",firebaseEvent.getEntity().toString());
    map.put("id",firebaseEvent.getId().toString());
    map.put("categoryId",firebaseEvent.getCategoryId().toString());
    map.put("categoryName",firebaseEvent.getCategoryName());
    map.put("isIndependentBatch",firebaseEvent.getIsIndependentBatch().toString());
    map.put("labSampleId", firebaseEvent.getLabSampleId() == null ? "" : firebaseEvent.getLabSampleId().toString());

    AndroidConfig androidConfig = AndroidConfig.builder()
        .setNotification(AndroidNotification.builder()
            .setChannelId(androidChannelId)
            .build())
        .build();

    tokenDtoList.forEach( dto -> {
      map.put("roleCategoryType",dto.getRoleCategoryType().toString());
      map.put("actionType",dto.getActionType().toString());
      Message message = Message
          .builder()
          .setAndroidConfig(androidConfig)
          .setToken(dto.getRegistrationToken())
          .setNotification(notification)
          .putAllData(map)
          .build();
      if (firebaseMessaging!= null){
        try{
          firebaseMessaging.send(message);
          log.info("Successfully Sent! "+ message + " state: " + firebaseEvent.getCurrentStateName());
        }catch (Exception e){
          HttpUtils.callDeleteAPI(iamBaseUrl+ "registration/token/" + dto.getRegistrationToken());
          log.info("error occurred " + e.getMessage());
        }
      }
    });

  }

  private List<NotificationUserTokenResponseDto> getTokens(FirebaseEvent firebaseEvent) throws JsonProcessingException {
    String url = String.format(iamBaseUrl + "registration/token?state=%s&categoryId=%d",firebaseEvent.getCurrentStateName(),firebaseEvent.getCategoryId());
    if(firebaseEvent.getManufacturerId()!=null)
      url += "&manufacturerId=" + firebaseEvent.getManufacturerId();
    if(firebaseEvent.getTargetManufacturerId()!=null)
      url += "&targetManufacturerId" + firebaseEvent.getTargetManufacturerId();
    if(firebaseEvent.getLabId()!=null)
      url += "&labId=" + firebaseEvent.getLabId();

    String response = HttpUtils.callGetAPI(url, null);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper.readValue(response,new TypeReference<List<NotificationUserTokenResponseDto>>() {});
  }


  public Long getNotificationCount() {
    Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
    Long labId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("labId", 0L).toString());
    UserCategoryListRequestDto dto = getLastReadTimeAndUserCategories();
    if(dto == null || (manufacturerId.equals(0L) && labId.equals(0L))){
      return 0L;
    }
    if(!manufacturerId.equals(0L)){
      List<String> targetCategoryNames = dto.getCategoryNames().stream()
          .map(c -> previousCategoryMap.getOrDefault(c, ""))
          .filter(value -> !value.isEmpty())
          .toList();
      return manager.getNotificationCountForModule(dto.getNotificationLastSeenTime(), dto.getCategoryNames(), manufacturerId, targetCategoryNames);
    } else{
      return manager.getNotificationCountForLab(dto.getNotificationLastSeenTime(), dto.getCategoryNames(), labId);
    }
  }

  public ListResponse<NotificationResponseDto> getNotifications(Integer pageNumber, Integer pageSize) {
    Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
    Long labId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("labId", 0L).toString());
    UserCategoryListRequestDto dto =  getLastReadTimeAndUserCategories();
    if(dto == null || (manufacturerId.equals(0L) && labId.equals(0L))){
      return new ListResponse<>();
    }
    List<NotificationEvent> entities;
    Long count;
    if(!manufacturerId.equals(0L)){
      List<String> targetCategoryNames = dto.getCategoryNames().stream()
          .map(c -> previousCategoryMap.getOrDefault(c, ""))
          .filter(value -> !value.isEmpty())
          .toList();
       entities = manager.getNotificationsForModule(dto.getCategoryNames(), manufacturerId, targetCategoryNames, pageNumber, pageSize);
       count = manager.getNotificationsCountForModule(dto.getCategoryNames(), manufacturerId, targetCategoryNames);
    } else{
      entities =  manager.getNotificationsForLab(dto.getCategoryNames(), labId, pageNumber, pageSize);
      count = manager.getNotificationsCountForLab(dto.getCategoryNames(), labId);
    }
    return ListResponse.from(entities, NotificationMapper::mapNotificationEntityToDto, count);
  }

  private UserCategoryListRequestDto getLastReadTimeAndUserCategories() {
    String url = iamBaseUrl + "user/notification/last-seen";
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    UserCategoryListRequestDto dto;
    try {
      String response = HttpUtils.callGetAPI(url, keycloakInfo.getAccessToken());
      dto = mapper.readValue(response, UserCategoryListRequestDto.class);
    } catch (Exception e){
      dto = null;
    }
    return dto;
  }

}
