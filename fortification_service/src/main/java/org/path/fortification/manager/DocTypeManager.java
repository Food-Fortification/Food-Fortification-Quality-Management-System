package org.path.fortification.manager;

import org.path.fortification.dao.DocTypeDao;
import org.path.fortification.entity.DocType;
import org.springframework.stereotype.Component;

@Component
public class DocTypeManager extends BaseManager<DocType, DocTypeDao> {
    public DocTypeManager(DocTypeDao dao) {
        super(dao);
    }
}
