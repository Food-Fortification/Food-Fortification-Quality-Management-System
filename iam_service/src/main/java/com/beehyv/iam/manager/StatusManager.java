package com.beehyv.iam.manager;

import com.beehyv.iam.dao.StatusDao;
import com.beehyv.iam.model.Status;
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
