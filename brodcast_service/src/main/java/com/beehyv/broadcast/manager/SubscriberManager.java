package com.beehyv.broadcast.manager;

import com.beehyv.broadcast.dao.SubscriberDao;
import com.beehyv.broadcast.model.Subscriber;
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
