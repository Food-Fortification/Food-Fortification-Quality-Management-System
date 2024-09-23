package com.beehyv.lab.manager;

import com.beehyv.lab.dao.SampleStateDao;
import com.beehyv.lab.entity.SampleState;
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
