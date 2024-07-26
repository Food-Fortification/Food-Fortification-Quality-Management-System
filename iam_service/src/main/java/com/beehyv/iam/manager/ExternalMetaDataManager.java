package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ExternalMetaDataDao;
import com.beehyv.iam.model.ExternalMetaData;
import org.springframework.stereotype.Component;

@Component
public class ExternalMetaDataManager extends BaseManager<ExternalMetaData, ExternalMetaDataDao>{

    private final ExternalMetaDataDao dao;
    public ExternalMetaDataManager(ExternalMetaDataDao dao) {
        super(dao);
        this.dao=dao;
    }

    public ExternalMetaData findByKey(String key){
        return dao.findByKey(key);
    }
}
