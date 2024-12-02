package org.path.lab.dao;

import org.path.lab.entity.Inspection;
import org.path.lab.enums.SampleType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class InspectionDao extends BaseDao<Inspection>{
  private final EntityManager em;


  public InspectionDao(EntityManager em) {
    super(em, Inspection.class);
    this.em = em;
  }

  public List<Long> findAllIdsBySampleType(SampleType sampleType, Long categoryId, String sampleState, Integer pageNumber, Integer pageSize, List<Long> testManufacturerIds) {
    String sampleId = sampleType == SampleType.batch ? "batchId" : "lotId";
    TypedQuery<Long> query = em.createQuery("Select Distinct(i.labSample." + sampleId +
        ") from Inspection i where i.labSample." + sampleId + " is not null " +
        "and i.labSample.categoryId = :categoryId " +
        "and (:sampleState is null or i.labSample.state.name = :sampleState) " +
        "and (:testManufacturerIdsNull is true or i.labSample.manufacturerId not in :testManufacturerIds) " +
        "order by i.labSample." + sampleId + " desc", Long.class);
    query.setParameter("sampleState",sampleState);
    query.setParameter("categoryId",categoryId);
    if (pageSize != null && pageNumber != null) {
      query.setFirstResult((pageNumber-1)*pageSize);
      query.setMaxResults(pageSize);
    }
    if (testManufacturerIds.isEmpty()) {
      query.setParameter("testManufacturerIdsNull", true);
      query.setParameter("testManufacturerIds", new ArrayList<Long>());
    } else {
      query.setParameter("testManufacturerIdsNull", false);
      query.setParameter("testManufacturerIds", testManufacturerIds);
    }
    return query.getResultList();
  }

  public Long getCountForSampleType(SampleType sampleType, Long categoryId, String sampleState, List<Long> testManufacturerIds) {
    String sampleId = sampleType == SampleType.batch ? "batchId" : "lotId";
    TypedQuery<Long> query = em.createQuery("Select Count(Distinct(i.labSample." + sampleId +
        ")) from Inspection i where i.labSample." + sampleId + " is not null " +
        "and i.labSample.categoryId = :categoryId " +
        "and (:sampleState is null or i.labSample.state.name = :sampleState) " +
        "and (:testManufacturerIdsNull is true or i.labSample.manufacturerId not in :testManufacturerIds)", Long.class);
    query.setParameter("sampleState",sampleState);
    query.setParameter("categoryId",categoryId);
    if (testManufacturerIds.isEmpty()) {
      query.setParameter("testManufacturerIdsNull", true);
      query.setParameter("testManufacturerIds", new ArrayList<Long>());
    } else {
      query.setParameter("testManufacturerIdsNull", false);
      query.setParameter("testManufacturerIds", testManufacturerIds);
    }
    return query.getSingleResult();
  }

  public Inspection findLatestSampleById(SampleType sampleType, Long id) {
    String sampleId = sampleType == SampleType.batch ? "batchId" : "lotId";
    TypedQuery<Inspection> query = em.createQuery("Select i from Inspection i where i.labSample." +
        sampleId + " IS NOT NULL " +
        "AND i.labSample." + sampleId + " = :id " +
        "ORDER BY i.createdDate desc ", Inspection.class);
    query.setParameter("id",id);
    query.setMaxResults(1);
    try{
      return query.getSingleResult();
    }catch (NoResultException e){
      return null;
    }
  }

  public boolean findSampleByLotNo(String lotNo){
    List<String> stateList = List.of("toReceive", "inProgress");
    TypedQuery<Inspection> query = em.createQuery("Select i from Inspection i where i.labSample.lotNo = :lotNo " +
            "and  i.labSample.state.name in :stateList", Inspection.class)
            .setParameter("stateList", stateList)
            .setParameter("lotNo",lotNo);
    try{
       return !query.getResultList().isEmpty();
    }catch (NoResultException e){
      return false;
    }
  }
}
