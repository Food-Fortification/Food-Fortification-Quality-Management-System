package org.path.iam.dao;

import org.path.iam.model.State;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class StateDao extends BaseDao<State>{
    private final EntityManager em;
    public StateDao(EntityManager em) {
        super(em, State.class);
        this.em = em;
    }
    public List<State> findAllByCountryId(Long countryId, String search, Integer pageNumber, Integer pageSize) {
        List<State> obj = null;
        TypedQuery<State> query = em
                .createQuery("from State s where s.country.id = :countryId AND s.geoId is NOT NULL AND (:search is null or s.name like :search) order by s.name asc", State.class)
                .setParameter("countryId", countryId);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCountForCountryIdAndSearch(Long countryId, String search) {
        TypedQuery<Long> query = em
                .createQuery("SELECT count(s.id) from State s where s.country.id = :countryId AND s.geoId is NOT NULL AND (:search is null or s.name like :search)", Long.class)
                .setParameter("countryId", countryId);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<State> findByCountryGeoId(String geoId) {
        try {
            return em.createQuery("from State s where s.country.geoId = :geoId ", State.class)
                    .setParameter("geoId",geoId)
                    .getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public State findByNameAndCountryId(String stateName, Long countryId){
        try {
            return em.createQuery("from State s where trim(lower(s.name)) = :stateName and s.country.id = :countryId", State.class)
                    .setParameter("stateName",stateName)
                    .setParameter("countryId",countryId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
