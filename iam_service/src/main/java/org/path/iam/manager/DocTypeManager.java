package org.path.iam.manager;

import org.path.iam.dao.DocTypeDao;
import org.path.iam.model.DocType;
import org.springframework.stereotype.Component;

@Component
public class DocTypeManager extends BaseManager<DocType, DocTypeDao>{
    public DocTypeManager(DocTypeDao dao) {
        super(dao);
    }
}
