package org.path.lab.manager;

import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.entity.LabTestType;
import org.path.lab.dao.LabTestTypeDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabTestTypeManager extends BaseManager<LabTestType, LabTestTypeDao>{
    private final LabTestTypeDao dao;

    public LabTestTypeManager(LabTestTypeDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<LabTestType> findAllByCategoryId(Long categoryId, String geoId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByCategoryId(categoryId, geoId, pageNumber, pageSize);
    }

    public List<LabTestType> findAllByType(LabTestType.Type type,Long categoryId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByType(type, categoryId, pageNumber, pageSize);
    }

  public List<LabTestType> findAllLabTestType(SearchListRequest searchListRequest,
      Integer pageNumber,
      Integer pageSize) {
    return dao.findAllLabTestType(searchListRequest, pageNumber, pageSize);
  }

  public Long getCountForAllLabTestType(SearchListRequest searchListRequest) {
    return dao.getCountForAllLabTestType(searchListRequest);
  }

}
