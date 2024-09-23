package com.beehyv.iam.dao;

import com.beehyv.iam.model.ExternalMetaData;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Component
public class ExternalMetaDataDao extends BaseDao<ExternalMetaData> {

    private final EntityManager em;
    public ExternalMetaDataDao(EntityManager em) {
        super(em, ExternalMetaData.class);
        this.em = em;
    }

    public ExternalMetaData findByKey(String key){
        try {
            return em.createQuery("from ExternalMetaData e where e.name = :key ", ExternalMetaData.class)
                    .setParameter("key",key)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}

