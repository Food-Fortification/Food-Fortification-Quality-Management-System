package com.beehyv.Immudb.dao;

import com.beehyv.Immudb.entity.NotificationEvent;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificationEventDao {

  private final EntityManager em;

  public NotificationEventDao(EntityManager em) {
    this.em = em;
  }

  @Transactional
  public void create(NotificationEvent obj) {
    em.persist(obj);
  }

  @Transactional
  public NotificationEvent update(NotificationEvent obj) {
    return em.merge(obj);
  }

  public NotificationEvent findById(Long id) {
    return em.find(NotificationEvent.class, id);
  }

  public List<NotificationEvent> findAll(Integer pageNumber, Integer pageSize) {
    List<NotificationEvent> obj;
    TypedQuery<NotificationEvent> query = em.createQuery(
        "from NotificationEvent ", NotificationEvent.class);
    if (pageSize != null && pageNumber != null) {
      query.setFirstResult((pageNumber - 1) * pageSize);
      query.setMaxResults(pageSize);
    }
    obj = query.getResultList();
    return obj;
  }

  public Long getCount() {
    return (Long) em.createQuery(
            "select count (b.id) from NotificationEvent as b")
        .getSingleResult();
  }

  @Transactional
  public void delete(Long id) {
    NotificationEvent obj = em.find(NotificationEvent.class, id);
    em.remove(obj);
  }

  public Long getNotificationCountForModule(LocalDateTime lastReadTime, List<String> categoryNames, Long manufacturerId, List<String> targetCategoryNames) {
    return em.createQuery(
            "select count(n.id) from NotificationEvent as n " +
                "WHERE ((:lastReadTime IS NULL OR  n.notificationDate > :lastReadTime )) " +
                "AND ((n.categoryName IN :categoryNames AND  n.manufacturerId=:manufacturerId) " +
                "OR (n.isTargetManufacturer=true AND n.targetManufacturerId=:manufacturerId AND n.categoryName IN :targetCategoryNames))",
            Long.class)
        .setParameter("lastReadTime", lastReadTime)
        .setParameter("categoryNames", categoryNames)
        .setParameter("manufacturerId", manufacturerId)
        .setParameter("targetCategoryNames", targetCategoryNames)
        .getSingleResult();
  }

  public List<NotificationEvent> getNotificationsForModule(List<String> categoryNames, Long manufacturerId, List<String> targetCategoryNames, Integer pageNumber, Integer pageSize) {

    TypedQuery<NotificationEvent> query = em.createQuery(
            "select n from NotificationEvent as n " +
                "WHERE ((n.categoryName IN :categoryNames AND  n.manufacturerId=:manufacturerId) " +
                "OR (n.isTargetManufacturer=true AND n.targetManufacturerId=:manufacturerId AND n.categoryName IN :targetCategoryNames)) " +
                "ORDER BY n.notificationDate desc",
            NotificationEvent.class)
        .setParameter("categoryNames", categoryNames)
        .setParameter("manufacturerId", manufacturerId)
        .setParameter("targetCategoryNames", targetCategoryNames);
    if(pageSize != null && pageNumber != null) {
      query.setFirstResult((pageNumber-1) * pageSize);
      query.setMaxResults(pageSize);
    }
    return query.getResultList();
  }
  public Long getNotificationsCountForModule(List<String> categoryNames, Long manufacturerId, List<String> targetCategoryNames) {
    TypedQuery<Long> query = em.createQuery(
                    "select count(n.id) from NotificationEvent as n " +
                            "WHERE ((n.categoryName IN :categoryNames AND  n.manufacturerId=:manufacturerId) " +
                            "OR (n.isTargetManufacturer=true AND n.targetManufacturerId=:manufacturerId AND n.categoryName IN :targetCategoryNames)) " +
                            "ORDER BY n.notificationDate desc",
                    Long.class)
            .setParameter("categoryNames", categoryNames)
            .setParameter("manufacturerId", manufacturerId)
            .setParameter("targetCategoryNames", targetCategoryNames);
    return query.getSingleResult();
  }

  public Long getNotificationCountForLab(LocalDateTime lastReadTime, List<String> categoryNames, Long labId) {
    return em.createQuery(
            "select count(n.id) from NotificationEvent as n " +
                "WHERE (:lastReadTime IS NULL OR  n.notificationDate > :lastReadTime ) " +
                "AND (n.categoryName IN :categoryNames AND  n.labId=:labId)",
            Long.class)
        .setParameter("lastReadTime", lastReadTime)
        .setParameter("categoryNames", categoryNames)
        .setParameter("labId", labId)
        .getSingleResult();
  }

  public List<NotificationEvent> getNotificationsForLab(List<String> categoryNames, Long labId, Integer pageNumber, Integer pageSize) {

    TypedQuery<NotificationEvent> query = em.createQuery(
            "select n from NotificationEvent as n " +
                "WHERE (n.categoryName IN :categoryNames AND  n.labId=:labId) " +
                "ORDER BY n.notificationDate desc",
            NotificationEvent.class)
        .setParameter("categoryNames", categoryNames)
        .setParameter("labId", labId);

    if(pageSize != null && pageNumber != null) {
      query.setFirstResult((pageNumber-1) * pageSize);
      query.setMaxResults(pageSize);
    }
    return query.getResultList();
  }

  public Long getNotificationsCountForLab(List<String> categoryNames, Long labId) {

    TypedQuery<Long> query = em.createQuery(
                    "select count(n.id) from NotificationEvent as n " +
                            "WHERE (n.categoryName IN :categoryNames AND  n.labId=:labId) " +
                            "ORDER BY n.notificationDate desc",
                    Long.class)
            .setParameter("categoryNames", categoryNames)
            .setParameter("labId", labId);
    return query.getSingleResult();
  }

  public List<NotificationEvent> findByEntityIdAndState(Long entityId, String state){

    TypedQuery<NotificationEvent> query = em.createQuery(
            "select n from NotificationEvent as n " +
                "WHERE (n.entityId = :entityId AND  n.currentStateName = :state) " +
                "ORDER BY n.id desc ",
            NotificationEvent.class)
        .setParameter("entityId", entityId)
        .setParameter("state", state);

    return query.getResultList();
  }
}
