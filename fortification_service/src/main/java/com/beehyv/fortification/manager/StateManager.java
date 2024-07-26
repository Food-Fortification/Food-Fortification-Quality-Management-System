package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.StateDao;
import com.beehyv.fortification.entity.State;
import com.beehyv.fortification.entity.StateType;
import com.beehyv.fortification.enums.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StateManager extends BaseManager<State, StateDao> {
    @Autowired
    private StateDao stateDao;

    public StateManager(StateDao stateDao) {
        super(stateDao);
        this.stateDao = stateDao;
    }

    public State findByName(String name) {
        return stateDao.findByName(name);
    }

    public List<State> findByNames(List<String> names) {
        return stateDao.findByNames(names);
    }

    public List<State> findByActionNames(List<String> actions, StateType actionType) {
        return stateDao.findByActionNames(actions, actionType);
    }

    public List<State> findAllByType(StateType type, Integer pageNumber, Integer pageSize) {
        return stateDao.findAllByType(type, pageNumber, pageSize);
    }

    public List<State> findAllByActionType(ActionType actionType) {
        return stateDao.findAllByActionType(actionType);
    }
}
