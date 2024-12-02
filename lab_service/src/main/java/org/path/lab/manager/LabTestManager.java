package org.path.lab.manager;

import org.path.lab.entity.LabTest;
import org.path.lab.dao.LabTestDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabTestManager extends BaseManager<LabTest, LabTestDao>{

    private final LabTestDao dao;
    public LabTestManager(LabTestDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<LabTest> findDetailsByBatchId(Long batchId,Integer pageNumber, Integer pageSize){
        return dao.findLabTestByBatchId(batchId,pageNumber,pageSize);
    }

}
