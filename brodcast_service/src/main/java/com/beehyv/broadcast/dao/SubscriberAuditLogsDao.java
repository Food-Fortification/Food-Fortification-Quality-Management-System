package com.beehyv.broadcast.dao;

import com.beehyv.broadcast.model.SubscriberAuditLogs;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class SubscriberAuditLogsDao extends BaseDao<SubscriberAuditLogs> {
    private final EntityManager em;

    public SubscriberAuditLogsDao(EntityManager em) {
        super(em, SubscriberAuditLogs.class);
        this.em = em;
    }
}
