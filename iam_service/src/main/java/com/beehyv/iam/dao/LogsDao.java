package com.beehyv.iam.dao;

import org.springframework.stereotype.Component;
import com.beehyv.iam.model.Logs;
import javax.persistence.EntityManager;

@Component
public class LogsDao extends BaseDao<Logs>{

    private final EntityManager em;

    public LogsDao(EntityManager em){
        super(em, Logs.class);
        this.em = em;

    }
}
