package org.path.lab.manager;

import org.path.lab.entity.LabTestReferenceMethod;
import org.path.lab.dao.LabTestReferenceMethodDao;
import org.path.lab.entity.LabTestType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabTestReferenceMethodManager extends BaseManager<LabTestReferenceMethod, LabTestReferenceMethodDao>{
    private final LabTestReferenceMethodDao dao;

    public LabTestReferenceMethodManager(LabTestReferenceMethodDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<LabTestReferenceMethod> findAllByTestTypeId(Long testTypeId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByTestTypeId(testTypeId, pageNumber, pageSize);
    }

    public List<LabTestReferenceMethod> findAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, LabTestType.Type type, String geoId) {
        if(geoId != null){
            List<LabTestReferenceMethod> labTestReferenceMethodList = dao.findAllByCategoryIdAndGeoId(categoryId, pageNumber, pageSize, type, geoId);
            if (!labTestReferenceMethodList.isEmpty()){
                return labTestReferenceMethodList;
            }
        }
        return dao.findAllByCategoryId(categoryId, pageNumber, pageSize, type);
    }

    public List<LabTestReferenceMethod> findAllByIds(List<Long>ids) {
        return dao.findAllByIds(ids);
    }
}
