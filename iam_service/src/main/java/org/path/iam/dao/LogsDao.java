package org.path.iam.dao;

import org.springframework.stereotype.Component;
import org.path.iam.model.Logs;
import javax.persistence.EntityManager;

@Component
public class LogsDao extends BaseDao<Logs>{

    private final EntityManager em;

    public LogsDao(EntityManager em){
        super(em, Logs.class);
        this.em = em;

    }
}
