package com.beehyv.lab.manager;

import com.beehyv.lab.entity.LabTest;
import com.beehyv.lab.dao.LabTestDao;
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
