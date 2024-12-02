package org.path.lab.dao;

import org.path.lab.entity.ExternalMetaData;
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

    public ExternalMetaData findByKeyAndService(String key, String service){
        try {
            return em.createQuery("from ExternalMetaData e where e.name = :key and e.externalService = :service ", ExternalMetaData.class)
                    .setParameter("service",service)
                    .setParameter("key",key)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
    public ExternalMetaData findByKey(String key){
        try{
            return em.createQuery("from ExternalMetaData e where e.name = :key ", ExternalMetaData.class)
                    .setParameter("key",key)
                    .getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }
}

