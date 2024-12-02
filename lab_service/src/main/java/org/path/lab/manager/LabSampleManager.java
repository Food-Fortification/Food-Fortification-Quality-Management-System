package org.path.lab.manager;

import org.path.lab.dao.LabSampleDao;
import org.path.lab.dto.requestDto.SearchListRequest;
import org.path.lab.entity.Lab;
import org.path.lab.entity.LabSample;
import org.path.lab.enums.SampleType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabSampleManager extends BaseManager<LabSample, LabSampleDao> {
    private LabSampleDao dao;
    public LabSampleManager(LabSampleDao dao) {
        super(dao);
        this.dao = dao;
    }
    public List<LabSample> findAll(Long labId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        return dao.findAll(labId, pageNumber, pageSize, searchRequest);
    }
    public List<LabSample> findAllSamplesForSuperAdmin(Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, List<Long> testManufacturerIds) {
        return dao.findAllSamplesForSuperAdmin(pageNumber, pageSize, searchRequest, testManufacturerIds);
    }
    public List<LabSample> findAllByCreateDate(List<Long>categoryList,String createStartDate,String createEndDate, Long labId) {
        return dao.findAllByCreateDate(categoryList,createStartDate,createEndDate,labId);
    }

    public List<LabSample> findAllSamplesByBatchId(Long batchId) {
        return dao.findAllSamplesByBatchId(batchId);
    }

    public List<LabSample> findAllSamplesByBatchIds(List<Long> batchIds) {
        return dao.findAllSamplesByBatchIds(batchIds);
    }

    public List<LabSample> findAllSamplesByBatchIdForInspection(Long batchId){
        return dao.findAllSamplesByBatchIdForInspection(batchId);
    }

    public List<LabSample> findAllSamplesByBatchIdsForInspection(List<Long> batchIds){
        return dao.findAllSamplesByBatchIdsForInspection(batchIds);
    }

    public List<LabSample> findAllSamplesByLotId(Long lotId) {
        return dao.findAllSamplesByLotId(lotId);
    }

    public List<LabSample> findAllSamplesByLotIds(List<Long> lotIds) {
        return dao.findAllSamplesByLotIds(lotIds);
    }

    public List<LabSample> findAllSamplesByLotIdForInspection(Long lotId) {
        return dao.findAllSamplesByLotIdForInspection(lotId);
    }

    public List<LabSample> findAllSamplesByLotIdsForInspection(List<Long> lotIds) {
        return dao.findAllSamplesByLotIdsForInspection(lotIds);
    }

    public Long getCount(int listSize, Long labId, Integer pageNumber, Integer pageSize, SearchListRequest searchRequest) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount(labId, searchRequest);
    }

    public Long getCountForSuperAdmin(int listSize,Integer pageNumber, Integer pageSize, SearchListRequest searchRequest, List<Long> testManufacturerIds) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCountSamplesForSuperAdmin(searchRequest, testManufacturerIds);
    }


    public List<Long[]> findAllAggregateByManufacturerIdAndLabIdAndCategoryId(Integer pageNumber, Integer pageSize) {
        return dao.findAllAggregateByManufacturerIdAndLabIdAndCategoryId(pageNumber, pageSize);
    }

    public LabSample findByCategoryIdAndId(Long categoryId, Long sampleId) {
        return dao.findByCategoryIdAndId(categoryId, sampleId);
    }

    public Lab getLabNameAddressByEntityId(SampleType entityType, Long entityId){
        return dao.getLabNameAddressByEntityId(entityType, entityId).getLab();
    }
}
