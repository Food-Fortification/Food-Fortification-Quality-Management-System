package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.DocTypeDao;
import com.beehyv.fortification.entity.DocType;
import org.springframework.stereotype.Component;

@Component
public class DocTypeManager extends BaseManager<DocType, DocTypeDao> {
    public DocTypeManager(DocTypeDao dao) {
        super(dao);
    }
}
