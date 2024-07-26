package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.BatchPropertyDao;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.BatchProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchPropertyManager extends BaseManager<BatchProperty, BatchPropertyDao> {
    private final BatchPropertyDao dao;
    public BatchPropertyManager(BatchPropertyDao dao) {
        super(dao);
        this.dao = dao;
    }
    public List<Batch> searchBatches(String search){
        return dao.searchBatches(search);
    }
    public Long getCountOfSearchedBatches(String search){
        return dao.getCountOfSearchedBatches(search);
    }

}
