package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.LotConsumedAggregateDao;
import com.beehyv.fortification.entity.LotConsumedAggregate;
import com.beehyv.fortification.enums.LotConsumedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LotConsumedAggregateManager extends BaseManager<LotConsumedAggregate, LotConsumedAggregateDao>{

    @Autowired
    private final LotConsumedAggregateDao dao;

    public LotConsumedAggregateManager(LotConsumedAggregateDao dao) {
        super(dao);
        this.dao = dao;
    }

    public LotConsumedAggregate findBySourceAndTargetDistrictGeoId(String sourceDistrictGeoId, String targetDistrictGeoId, LotConsumedType lotConsumedType){
        return dao.findBySourceAndTargetDistrictGeoId(sourceDistrictGeoId, targetDistrictGeoId, lotConsumedType);
    }
}
