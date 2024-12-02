package org.path.fortification.manager;

import org.path.fortification.dao.LotConsumedAggregateDao;
import org.path.fortification.entity.LotConsumedAggregate;
import org.path.fortification.enums.LotConsumedType;
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
