package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.ExternalMetaDataDao;
import com.beehyv.fortification.entity.ExternalMetaData;
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


}
