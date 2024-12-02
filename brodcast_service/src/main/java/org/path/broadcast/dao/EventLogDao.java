package org.path.broadcast.dao;

import org.path.broadcast.model.EventLog;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Component
public class EventLogDao extends BaseDao<EventLog> {
    private final EntityManager em;

    public EventLogDao(EntityManager em) {
        super(em, EventLog.class);
        this.em = em;
    }

    public void saveEventLog(EventLog eventLog) {
        em.persist(eventLog);
    }

    public List<EventLog> getRetryEvents() {
        return (List<EventLog>) em.createQuery("select e from EventLog e where isRetry = true").getResultList();
    }

    public Optional<EventLog> getEventLogByEvent(String eventString, String serviceName) {
        try {
            return Optional.of((EventLog) em.createQuery("select e from EventLog e where e.eventString = :eventString and e.serviceName = :serviceName")
                    .setParameter("eventString", eventString)
                    .setParameter("serviceName", serviceName)
                    .getSingleResult());
        } catch (NoResultException noResultException) {
            return Optional.empty();
        }
    }
}