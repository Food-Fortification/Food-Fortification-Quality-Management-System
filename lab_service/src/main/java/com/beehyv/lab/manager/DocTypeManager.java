package com.beehyv.lab.manager;

import com.beehyv.lab.entity.DocType;
import com.beehyv.lab.dao.DocTypeDao;
import org.springframework.stereotype.Component;

@Component
public class DocTypeManager extends BaseManager<DocType, DocTypeDao>{
    public DocTypeManager(DocTypeDao dao) {
        super(dao);
    }
}
