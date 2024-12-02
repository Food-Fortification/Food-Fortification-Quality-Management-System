package org.path.iam.dao;

import org.path.iam.model.NotificationUserToken;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificationUserTokenDao {

  private final EntityManager em;

  public NotificationUserTokenDao(EntityManager em) {
    this.em = em;
  }

  @Transactional
  public void create(NotificationUserToken obj) {
    em.persist(obj);
  }

  @Transactional
  public NotificationUserToken update(NotificationUserToken obj) {
    return em.merge(obj);
  }

  public NotificationUserToken findById(Long id) {
    return em.find(NotificationUserToken.class, id);
  }

  public List<NotificationUserToken> findAll(Integer pageNumber, Integer pageSize) {
    List<NotificationUserToken> obj ;
    TypedQuery<NotificationUserToken> query = em.createQuery(
        "from " + NotificationUserToken.class.getSimpleName(), NotificationUserToken.class);
    if (pageSize != null && pageNumber != null) {
      query.setFirstResult((pageNumber - 1) * pageSize);
      query.setMaxResults(pageSize);
    }
    obj = query.getResultList();
    return obj;
  }

  public Long getCount() {
    return (Long) em.createQuery(
            "select count (b.id) from " + NotificationUserToken.class.getSimpleName() + " as b")
        .getSingleResult();
  }

  @Transactional
  public void delete(Long id) {
    NotificationUserToken obj = em.find(NotificationUserToken.class, id);
    em.remove(obj);
  }

  public NotificationUserToken findByRegistrationToken(String registrationToken, Long userId) {
    NotificationUserToken token;
    try {
      token = em.createQuery(
              "From NotificationUserToken t where t.registrationToken = :registrationToken and t.user.id = :userId",
              NotificationUserToken.class)
          .setParameter("registrationToken", registrationToken)
          .setParameter("userId", userId)
              .getSingleResult();
    } catch (NoResultException e) {
      token = null;
    }
    return token;
  }

}
