package org.path.fortification.manager;

import org.path.fortification.dao.StatusDao;
import org.path.fortification.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusManager extends BaseManager<Status, StatusDao> {
    public StatusManager(StatusDao dao) {
        super(dao);
    }
}
