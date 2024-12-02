package org.path.lab.manager;

import org.path.lab.dao.SamplePropertyDao;
import org.path.lab.entity.SampleProperty;
import org.springframework.stereotype.Component;

@Component
public class SamplePropertyManager extends BaseManager<SampleProperty, SamplePropertyDao> {
    public SamplePropertyManager(SamplePropertyDao dao) {
        super(dao);
    }
}
