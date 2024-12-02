package org.path.iam.manager;

import org.path.iam.dao.NotificationStateRoleMappingDao;
import org.path.iam.model.NotificationStateRoleMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationStateRoleMappingManager {
    private final NotificationStateRoleMappingDao notificationStateRoleMappingDao;

    public NotificationStateRoleMappingManager(NotificationStateRoleMappingDao notificationStateRoleMappingDao) {
        this.notificationStateRoleMappingDao = notificationStateRoleMappingDao;
    }

    public List<NotificationStateRoleMapping> findByStateAndCategory(String state, Long categoryId){
        return notificationStateRoleMappingDao.findByStateAndCategory(state, categoryId);
    }
}
