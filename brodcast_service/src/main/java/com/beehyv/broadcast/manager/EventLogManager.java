package com.beehyv.broadcast.manager;


import com.beehyv.broadcast.dao.EventLogDao;
import com.beehyv.broadcast.model.EventLog;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventLogManager extends BaseManager<EventLog, EventLogDao> {
    private final EventLogDao dao;
    private final KeycloakInfo keycloakInfo;

    public EventLogManager(EventLogDao dao, KeycloakInfo keycloakInfo) {
        super(dao);
        this.dao = dao;
        this.keycloakInfo = keycloakInfo;
    }

    public void saveEventLog(EventLog eventLog) {
        dao.saveEventLog(eventLog);
    }

    public List<EventLog> getRetryEvents() {
        return dao.getRetryEvents();
    }

    public Optional<EventLog> getEventLogByEvent(String eventString, String serviceName) {
        return dao.getEventLogByEvent(eventString, serviceName);
    }
}
