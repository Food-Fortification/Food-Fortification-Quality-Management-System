package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.StatusDao;
import com.beehyv.fortification.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusManager extends BaseManager<Status, StatusDao> {
    public StatusManager(StatusDao dao) {
        super(dao);
    }
}
