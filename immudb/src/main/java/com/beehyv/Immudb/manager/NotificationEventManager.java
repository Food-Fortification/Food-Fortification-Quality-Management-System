package com.beehyv.Immudb.manager;

import com.beehyv.Immudb.dao.NotificationEventDao;
import com.beehyv.Immudb.entity.NotificationEvent;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventManager {

  private final NotificationEventDao dao;
  public NotificationEventManager(NotificationEventDao dao) {
    this.dao = dao;
  }

  public NotificationEvent create(NotificationEvent entity) {
    dao.create(entity);
    return entity;
  }

  public NotificationEvent update(NotificationEvent entity) {
    return dao.update(entity);
  }

  public NotificationEvent findById(Long id) {
    return dao.findById(id);
  }

  public Long getCount(int listSize, Integer pageNumber, Integer pageSize) {
    if(pageSize == null || pageNumber == null) {
      return ((Integer) listSize).longValue();
    }
    return dao.getCount();
  }
  public List<NotificationEvent> findAll(Integer pageNumber, Integer pageSize) {
    return dao.findAll(pageNumber, pageSize);
  }

  public Long getNotificationCountForModule(LocalDateTime lastReadTIme, List<String> categoryNames, Long manufacturerId, List<String> targetCategoryNames){
    return dao.getNotificationCountForModule(lastReadTIme, categoryNames, manufacturerId, targetCategoryNames);
  }

  public List<NotificationEvent> getNotificationsForModule(List<String> categoryNames, Long manufacturerId, List<String> targetCategoryNames, Integer pageNumber, Integer pageSize){
    return dao.getNotificationsForModule(categoryNames, manufacturerId, targetCategoryNames, pageNumber, pageSize);
  }
  public Long getNotificationsCountForModule(List<String> categoryNames, Long manufacturerId, List<String> targetCategoryNames){
    return dao.getNotificationsCountForModule(categoryNames, manufacturerId, targetCategoryNames);
  }

  public Long getNotificationCountForLab(LocalDateTime lastReadTIme, List<String> categoryNames, Long labId){
    return dao.getNotificationCountForLab(lastReadTIme, categoryNames, labId);
  }

  public List<NotificationEvent> getNotificationsForLab(List<String> categoryNames, Long labId, Integer pageNumber, Integer pageSize){
    return dao.getNotificationsForLab(categoryNames, labId, pageNumber, pageSize);
  }
  public Long getNotificationsCountForLab(List<String> categoryNames, Long labId){
    return dao.getNotificationsCountForLab(categoryNames, labId);
  }

  public void delete(Long id) {
    dao.delete(id);
  }

  public NotificationEvent findByEntityIdAndState(Long entityId, String state){
    return dao.findByEntityIdAndState(entityId, state).get(0);
  }
}
