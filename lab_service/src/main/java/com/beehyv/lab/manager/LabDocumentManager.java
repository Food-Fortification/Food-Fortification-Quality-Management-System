package com.beehyv.lab.manager;

import com.beehyv.lab.entity.LabDocument;
import com.beehyv.lab.dao.LabDocumentDao;
import org.springframework.stereotype.Component;

@Component
public class LabDocumentManager extends BaseManager<LabDocument, LabDocumentDao>{
    public LabDocumentManager(LabDocumentDao dao) {
        super(dao);
    }
}
