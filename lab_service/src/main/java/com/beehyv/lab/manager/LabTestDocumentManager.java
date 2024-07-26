package com.beehyv.lab.manager;

import com.beehyv.lab.entity.LabTestDocument;
import com.beehyv.lab.dao.LabTestDocumentDao;
import org.springframework.stereotype.Component;

@Component
public class LabTestDocumentManager extends BaseManager<LabTestDocument, LabTestDocumentDao>{
    public LabTestDocumentManager(LabTestDocumentDao dao) {
        super(dao);
    }
}
