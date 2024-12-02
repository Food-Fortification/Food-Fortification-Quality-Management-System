package org.path.lab.dao;

import org.path.lab.entity.Base;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class BaseDao<T extends Base> {
    private final Class<T> tClass;
    private final EntityManager em;

    public BaseDao(EntityManager em, Class<T> tClass) {
        this.em = em;
        this.tClass = tClass;
    }

    @Transactional
    public T create(T obj) {
        em.persist(obj);
        return obj;
    }

    @Transactional
    public T update(T obj) {
        return em.merge(obj);
    }

    public T findById(Long id) {
        return em.find(tClass, id);
    }

    public List<T> findAll(Integer pageNumber, Integer pageSize) {
        List<T> obj = null;
        TypedQuery<T> query = em.createQuery("from " + tClass.getSimpleName(), tClass);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        obj = query.getResultList();
        return obj;
    }

    public Long getCount() {
        return (Long) em.createQuery("select count (b.id) from " + tClass.getSimpleName() +" as b")
                .getSingleResult();
    }

    @Transactional
    public T delete(Long id) {
        T obj = em.find(tClass, id);
        em.remove(obj);
        return obj;
    }
}