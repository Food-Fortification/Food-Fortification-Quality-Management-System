package org.path.iam.manager;

import org.path.iam.dao.StatusDao;
import org.path.iam.model.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusManager extends BaseManager<Status, StatusDao>{
    private final StatusDao dao;
    public StatusManager(StatusDao dao) {
        super(dao);
        this.dao = dao;
    }
    public Status findByName(String name){
        return dao.findByName(name);
    }
}
