package com.beehyv.iam.manager;

import com.beehyv.iam.dao.DocTypeDao;
import com.beehyv.iam.model.DocType;
import org.springframework.stereotype.Component;

@Component
public class DocTypeManager extends BaseManager<DocType, DocTypeDao>{
    public DocTypeManager(DocTypeDao dao) {
        super(dao);
    }
}
