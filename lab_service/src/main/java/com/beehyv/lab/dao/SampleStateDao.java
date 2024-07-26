package com.beehyv.lab.dao;

import com.beehyv.lab.entity.SampleState;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class SampleStateDao extends BaseDao<SampleState>{
    private final EntityManager em;
    public SampleStateDao(EntityManager em) {
        super(em, SampleState.class);
        this.em = em;
    }
    public SampleState findByName(String name){
        return em.createQuery("from SampleState where name = :name", SampleState.class)
                .setParameter("name",name)
                .getSingleResult();
    }

    public List<SampleState> findAllByNames(List<String> names) {
        return em.createQuery("from SampleState s where s.name in :names", SampleState.class)
                .setParameter("names",names)
                .getResultList();
    }
}
