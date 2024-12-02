package org.path.fortification.dao;

import org.path.fortification.entity.State;
import org.path.fortification.entity.StateType;
import org.path.fortification.enums.ActionType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class StateDao extends BaseDao<State>{

    @PersistenceContext
    private EntityManager entityManager;

    public StateDao(EntityManager entityManager) {
        super(entityManager, State.class);
        this.entityManager = entityManager;
    }

    @Override
    public List<State> findAll(Integer pageNumber, Integer pageSize) {
        TypedQuery<State> query = entityManager.createQuery("from State t order by t.id asc", State.class);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public State findByName(String name) {
        return entityManager.createQuery("from State where name = :name", State.class)
                .setParameter("name", name).getSingleResult();
    }

    public List<State> findByNames(List<String> names) {
        return entityManager.createQuery("from State where name in :names  order by id asc", State.class)
                .setParameter("names", names).getResultList();
    }

    public List<State> findAllByType(StateType type, Integer pageNumber, Integer pageSize) {
        TypedQuery<State> query = entityManager.createQuery("from State s where s.type = :type", State.class)
                .setParameter("type", type);
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public List<State> findAllByActionType(ActionType actionType) {
        return entityManager.createQuery("from State s where s.actionType = :actionType", State.class)
                .setParameter("actionType", actionType)
                .getResultList();
    }

    public List<State> findByActionNames(List<String> actions, StateType stateType) {
        return entityManager.createQuery("from State where actionName in :actions and type = :stateType order by id asc", State.class)
                .setParameter("actions", actions)
                .setParameter("stateType", stateType)
                .getResultList();
    }
}