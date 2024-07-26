package com.beehyv.iam.manager;

import com.beehyv.iam.dao.LabUsersDao;
import com.beehyv.iam.model.LabUsers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabUsersManager extends BaseManager<LabUsers, LabUsersDao> {
    private final LabUsersDao dao;
    public LabUsersManager(LabUsersDao dao) {
        super(dao);
        this.dao=dao;
    }

    public List<LabUsers>findUsersByLabId(Long labId){
        return dao.findUsersByLabId(labId);
    }
}
