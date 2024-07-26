package com.beehyv.lab.manager;

import com.beehyv.lab.dao.SamplePropertyDao;
import com.beehyv.lab.entity.SampleProperty;
import org.springframework.stereotype.Component;

@Component
public class SamplePropertyManager extends BaseManager<SampleProperty, SamplePropertyDao> {
    public SamplePropertyManager(SamplePropertyDao dao) {
        super(dao);
    }
}
