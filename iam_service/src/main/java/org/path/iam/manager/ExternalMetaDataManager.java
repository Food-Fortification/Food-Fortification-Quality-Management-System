package org.path.iam.manager;

import org.path.iam.dao.ExternalMetaDataDao;
import org.path.iam.model.ExternalMetaData;
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
