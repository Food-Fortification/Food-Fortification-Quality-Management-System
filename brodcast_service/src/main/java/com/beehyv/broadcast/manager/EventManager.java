package com.beehyv.broadcast.manager;

import com.beehyv.broadcast.dao.EventDao;
import com.beehyv.broadcast.model.Event;
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
