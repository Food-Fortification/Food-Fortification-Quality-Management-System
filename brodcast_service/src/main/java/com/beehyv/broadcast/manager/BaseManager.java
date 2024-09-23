package com.beehyv.broadcast.manager;


import com.beehyv.broadcast.dao.BaseDao;
import com.beehyv.broadcast.model.Base;

import java.util.List;

public abstract class BaseManager<T extends Base, D extends BaseDao<T>> {
    private final D dao;

    public BaseManager(D dao) {
        this.dao = dao;
    }

    public T create(T entity) {
        return dao.create(entity);
    }

    public T update(T entity) {
        return dao.update(entity);
    }

    public T findById(Long id) {
        return dao.findById(id);
    }

    public Long getCount(int listSize, Integer pageNumber, Integer pageSize) {
        if (pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount();
    }

    public List<T> findAll(Integer pageNumber, Integer pageSize) {
        return dao.findAll(pageNumber, pageSize);
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}
