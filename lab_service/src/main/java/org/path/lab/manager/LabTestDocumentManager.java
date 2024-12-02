package org.path.lab.manager;

import org.path.lab.entity.LabTestDocument;
import org.path.lab.dao.LabTestDocumentDao;
import org.springframework.stereotype.Component;

@Component
public class LabTestDocumentManager extends BaseManager<LabTestDocument, LabTestDocumentDao>{
    public LabTestDocumentManager(LabTestDocumentDao dao) {
        super(dao);
    }
}
