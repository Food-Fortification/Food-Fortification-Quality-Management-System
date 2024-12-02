package org.path.lab.manager;

import org.path.lab.entity.Base;
import org.path.lab.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public abstract class BaseManager<T extends Base, D extends BaseDao<T>> {
    private final D dao;

    public BaseManager(D dao) {
        this.dao = dao;
    }

    @Transactional
    public T create(T entity) {
        return dao.create(entity);
    }

    @Transactional
    public T update(T entity) {
        return dao.update(entity);
    }

    public T findById(Long id) {
        return dao.findById(id);
    }

    public List<T> findAll(Integer pageNumber, Integer pageSize) {
        return dao.findAll(pageNumber, pageSize);
    }

    @Transactional
    public T delete(Long id) {
        return dao.delete(id);
    }

    public Long getCount(int listSize, Integer pageNumber, Integer pageSize) {
        if(pageSize == null || pageNumber == null) {
            return ((Integer) listSize).longValue();
        }
        return dao.getCount();
    }
}
