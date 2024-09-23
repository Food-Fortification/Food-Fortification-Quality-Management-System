package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.LotConsumedAggregate;
import com.beehyv.fortification.enums.LotConsumedType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Component
public class LotConsumedAggregateDao extends BaseDao<LotConsumedAggregate> {

    private final EntityManager em;
    public LotConsumedAggregateDao(EntityManager em) {
        super(em, LotConsumedAggregate.class);
        this.em = em;
    }

    public LotConsumedAggregate findBySourceAndTargetDistrictGeoId(String sourceDistrictGeoId, String targetDistrictGeoId, LotConsumedType lotConsumedType){
        try{
            return (LotConsumedAggregate) em.createQuery("select l from LotConsumedAggregate as l " +
                            "where l.sourceDistrictGeoId = :sourceDistrictGeoId " +
                            "and l.targetDistrictGeoId = :targetDistrictGeoId " +
                            "and l.lotConsumedType = :lotConsumedType ")
                    .setParameter("sourceDistrictGeoId", sourceDistrictGeoId)
                    .setParameter("targetDistrictGeoId", targetDistrictGeoId)
                    .setParameter("lotConsumedType", lotConsumedType)
                    .getSingleResult();
        }catch (NoResultException exception) {
            return null;
        }
    }
}
