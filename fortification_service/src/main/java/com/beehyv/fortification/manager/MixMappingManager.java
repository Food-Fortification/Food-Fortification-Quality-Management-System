package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.MixMappingDao;
import com.beehyv.fortification.entity.MixMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MixMappingManager extends BaseManager<MixMapping, MixMappingDao> {
    private final MixMappingDao dao;
    public MixMappingManager(MixMappingDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<MixMapping> findAllByTargetBatchId(Long targetBatchId, Integer pageNumber, Integer pageSize) {
        return dao.findAllByTargetBatchId(targetBatchId, pageNumber, pageSize);
    }

    public Long getCountByTargetBatchId(int listSize, Long batchId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCountByTargetBatchId(batchId);
    }

    public Long getCountBySourceLotId(int listSize, Long sourceLotId, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCountBySourceLotId(sourceLotId);
    }

    public List<MixMapping> findAllBySourceLotId(Long sourceLotId, Integer pageNumber, Integer pageSize) {
        return dao.findAllBySourceLotId(sourceLotId, pageNumber, pageSize);
    }
    public List<MixMapping> findAllByIds(List<Long> ids){
        return dao.findAllByIds(ids);
    }
}
