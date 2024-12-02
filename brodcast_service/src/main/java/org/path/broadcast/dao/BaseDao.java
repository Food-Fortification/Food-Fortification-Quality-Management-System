package org.path.broadcast.dao;

import org.path.broadcast.model.Base;
import org.path.parent.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
        return Optional.ofNullable(em.find(tClass, id))
                .orElseThrow(() -> new ResourceNotFoundException(tClass.getSimpleName(), "id", id));
    }

    public List<T> findAll(Integer pageNumber, Integer pageSize) {
        TypedQuery<T> query = em.createQuery("from " + tClass.getSimpleName() + " t order by t.id desc", tClass);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getCount() {
        return (Long) em.createQuery("select count (b.id) from " + tClass.getSimpleName() + " as b")
                .getSingleResult();
    }

    @Transactional
    public void delete(Long id) {
        T obj = em.find(tClass, id);
        em.remove(obj);
    }
}
