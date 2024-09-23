package com.beehyv.lab.manager;

import com.beehyv.lab.dao.ExternalMetaDataDao;
import com.beehyv.lab.entity.ExternalMetaData;
import org.springframework.stereotype.Component;

@Component
public class ExternalMetaDataManager extends BaseManager<ExternalMetaData, ExternalMetaDataDao>{

    private final ExternalMetaDataDao dao;
    public ExternalMetaDataManager(ExternalMetaDataDao dao) {
        super(dao);
        this.dao=dao;
    }

    public ExternalMetaData findByKeyAndService(String key, String service){
        return dao.findByKeyAndService(key, service);
    }
    public ExternalMetaData findByKey(String key){
        return dao.findByKey(key);
    }
}
