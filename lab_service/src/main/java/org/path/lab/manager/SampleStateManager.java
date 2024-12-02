package org.path.lab.manager;

import org.path.lab.dao.SampleStateDao;
import org.path.lab.entity.SampleState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleStateManager extends BaseManager<SampleState, SampleStateDao>{
    private final SampleStateDao dao;
    public SampleStateManager(SampleStateDao dao) {
        super(dao);
        this.dao = dao;
    }
    public SampleState findByName(String name){
        return dao.findByName(name);
    }
    public List<SampleState> findAllByNames(List<String> names){
        return dao.findAllByNames(names);
    }
}
