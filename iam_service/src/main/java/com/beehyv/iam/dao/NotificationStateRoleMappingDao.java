package com.beehyv.iam.dao;

import com.beehyv.iam.model.NotificationStateRoleMapping;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Component
public class NotificationStateRoleMappingDao {
    private final EntityManager em;
    public NotificationStateRoleMappingDao(EntityManager em) {
        super();
        this.em = em;
    }
    public List<NotificationStateRoleMapping> findByStateAndCategory(String state, Long categoryId){
        try {
            return em
                    .createQuery("from NotificationStateRoleMapping s where notificationState.name = :state and notificationState.categoryId = :categoryId", NotificationStateRoleMapping.class)
                    .setParameter("state", state)
                    .setParameter("categoryId",categoryId)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }


    }

