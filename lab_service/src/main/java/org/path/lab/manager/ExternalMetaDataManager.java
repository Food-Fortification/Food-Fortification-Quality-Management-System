package org.path.lab.manager;

import org.path.lab.dao.ExternalMetaDataDao;
import org.path.lab.entity.ExternalMetaData;
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
