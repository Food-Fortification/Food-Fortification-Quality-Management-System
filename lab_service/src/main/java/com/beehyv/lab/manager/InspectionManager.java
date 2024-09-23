package com.beehyv.lab.manager;

import com.beehyv.lab.dao.InspectionDao;
import com.beehyv.lab.entity.Inspection;
import com.beehyv.lab.enums.SampleType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InspectionManager extends BaseManager<Inspection, InspectionDao> {
  private final InspectionDao dao;

  public InspectionManager(InspectionDao dao) {
    super(dao);
    this.dao = dao;
  }

  public List<Long> findAllIdsBySampleType(SampleType sampleType, Long categoryId, String sampleState, Integer pageNumber, Integer pageSize, List<Long> testManufacturerIds) {
    return dao.findAllIdsBySampleType(sampleType, categoryId, sampleState, pageNumber,pageSize, testManufacturerIds);
  }

  public Long getCountForSampleType(int size, SampleType sampleType, Long categoryId, String sampleState, Integer pageNumber, Integer pageSize, List<Long> testManufacturerIds) {
    if(pageSize == null || pageNumber == null) {
      return ((Integer) size).longValue();
    }
    return dao.getCountForSampleType(sampleType, categoryId, sampleState, testManufacturerIds);
  }

  public Inspection findLatestSampleById(SampleType sampleType, Long id) {
    return dao.findLatestSampleById(sampleType,id);
  }

  public boolean findSampleByLotNo(String lot){
    return dao.findSampleByLotNo(lot);
  }

}
