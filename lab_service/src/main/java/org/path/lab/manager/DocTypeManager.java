package org.path.lab.manager;

import org.path.lab.entity.DocType;
import org.path.lab.dao.DocTypeDao;
import org.springframework.stereotype.Component;

@Component
public class DocTypeManager extends BaseManager<DocType, DocTypeDao>{
    public DocTypeManager(DocTypeDao dao) {
        super(dao);
    }
}
