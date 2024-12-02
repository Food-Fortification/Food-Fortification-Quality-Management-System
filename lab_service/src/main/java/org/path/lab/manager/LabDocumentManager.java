package org.path.lab.manager;

import org.path.lab.entity.LabDocument;
import org.path.lab.dao.LabDocumentDao;
import org.springframework.stereotype.Component;

@Component
public class LabDocumentManager extends BaseManager<LabDocument, LabDocumentDao>{
    public LabDocumentManager(LabDocumentDao dao) {
        super(dao);
    }
}
