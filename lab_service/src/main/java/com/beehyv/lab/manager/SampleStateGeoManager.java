package com.beehyv.lab.manager;

import com.beehyv.lab.dao.SampleStateGeoDao;
import com.beehyv.lab.dto.responseDto.SampleStateCountResponseDto;
import com.beehyv.lab.entity.SampleStateGeo;
import com.beehyv.lab.entity.SampleStateGeoId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class SampleStateGeoManager extends BaseManager<SampleStateGeo, SampleStateGeoDao> {
    private final SampleStateGeoDao dao;
    public SampleStateGeoManager(SampleStateGeoDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Long[] getSamplesGeoCount(Long categoryId, String filterBy, String geoId, List<Long> activeLabIds, Integer year) {
        return dao.getSamplesGeoCount(categoryId, filterBy, geoId, activeLabIds, year);
    }

    public List<SampleStateCountResponseDto> getAggregatedGeoSamplesCount(Long categoryId, String groupBy, String filterBy,
                                                                          String geoId, List<Long> activeLabIds, Integer year) {
        return dao.getAggregatedGeoSamplesCount(categoryId, groupBy, filterBy, geoId, activeLabIds, year);
    }

    public List<SampleStateGeo> findAllByCategoryIdAndGeoId(Long categoryId, String filterBy, String geoId, Set<Long> activeLabIds,
                                                            Integer year, Integer pageNumber, Integer pageSize, String search) {
        return dao.findAllByCategoryIdAndGeoId(categoryId, filterBy, geoId, activeLabIds, year, pageNumber, pageSize, search);
    }
    public Long findAllByCategoryIdAndGeoIdCount(int listSize, Long categoryId, String filterBy, String geoId,
                                                 Integer year, Integer pageNumber, Integer pageSize,
                                                 Set<Long> activeLabIds, String search) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.findAllByCategoryIdAndGeoIdCount(categoryId, filterBy, geoId, year, activeLabIds, search);
    }

    @Transactional
    public void saveOrUpdate(SampleStateGeo sampleStateGeo) {
        this.update(sampleStateGeo);
    }

    public SampleStateGeo findByCategoryIdAndLabIdAndGeoIds(SampleStateGeoId sampleStateGeoId) {
        return dao.findByCategoryIdAndLabIdAndGeoIds(sampleStateGeoId);
    }
}
