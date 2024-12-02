package org.path.fortification.manager;

import org.path.fortification.dao.BatchNoSequenceDao;
import org.path.fortification.entity.BatchNoId;
import org.path.fortification.entity.BatchNoSequence;
import org.springframework.stereotype.Component;

@Component
public class BatchNoSequenceManager extends BaseManager<BatchNoSequence, BatchNoSequenceDao> {
    private final BatchNoSequenceDao dao;
    public BatchNoSequenceManager(BatchNoSequenceDao dao) {
        super(dao);
        this.dao = dao;
    }

    public BatchNoSequence findById(BatchNoId batchNoId) {
        return dao.findById(batchNoId);
    }

    @Override
    public BatchNoSequence update(BatchNoSequence batchNoSequence) {
        batchNoSequence.setSequence(batchNoSequence.getSequence() + 1);
        return dao.update(batchNoSequence);
    }
}
