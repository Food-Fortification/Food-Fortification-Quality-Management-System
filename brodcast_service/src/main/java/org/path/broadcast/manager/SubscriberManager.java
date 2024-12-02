package org.path.broadcast.manager;

import org.path.broadcast.dao.SubscriberDao;
import org.path.broadcast.model.Subscriber;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriberManager extends BaseManager<Subscriber, SubscriberDao> {

    private final SubscriberDao dao;

    public SubscriberManager(SubscriberDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<Subscriber> findByGeoId(Integer stateGeoId) {
        return dao.findByGeoId(stateGeoId);
    }
}
