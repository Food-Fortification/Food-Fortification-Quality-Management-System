package org.path.broadcast.manager;

import org.path.broadcast.dao.EventDao;
import org.path.broadcast.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventManager extends BaseManager<Event, EventDao> {

    private final EventDao dao;

    public EventManager(EventDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Event findByName(String name) {
        return dao.findByName(name);
    }

    public Event findById(Long id) {
        return dao.findById(id);
    }
}
