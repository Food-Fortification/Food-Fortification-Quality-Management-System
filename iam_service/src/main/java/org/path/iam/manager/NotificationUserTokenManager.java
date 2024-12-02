package org.path.iam.manager;

import org.path.iam.dao.NotificationUserTokenDao;
import org.path.iam.model.NotificationUserToken;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotificationUserTokenManager {
    private final NotificationUserTokenDao dao;
    public NotificationUserTokenManager(NotificationUserTokenDao dao) {
        this.dao = dao;
    }

    public NotificationUserToken create(NotificationUserToken entity) {
        dao.create(entity);
        return entity;
    }

    public NotificationUserToken update(NotificationUserToken entity) {
        return dao.update(entity);
    }

    public NotificationUserToken findById(Long id) {
        return dao.findById(id);
    }

    public Long getCount(int listSize, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount();
    }
    public List<NotificationUserToken> findAll(Integer pageNumber, Integer pageSize) {
        return dao.findAll(pageNumber, pageSize);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public NotificationUserToken findByRegistrationToken(String registrationToken, Long userId){
        return dao.findByRegistrationToken(registrationToken, userId);
    }

}
