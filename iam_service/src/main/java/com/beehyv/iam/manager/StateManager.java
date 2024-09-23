package com.beehyv.iam.manager;

import com.beehyv.iam.dao.StateDao;
import com.beehyv.iam.model.State;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class StateManager extends BaseManager<State, StateDao> {
    private final StateDao dao;
    public StateManager(StateDao dao) {
        super(dao);
        this.dao = dao;
    }
    public List<State> findAllByCountryId(Long countryId, String search, Integer pageNumber, Integer pageSize){
        return dao.findAllByCountryId(countryId, search, pageNumber, pageSize);
    }

    public Long getCountForCountryIdAndSearch(Long countryId, String search) {
        return dao.getCountForCountryIdAndSearch(countryId, search);
    }

    public List<State> findByCountryGeoId(String geoId) {
        return dao.findByCountryGeoId(geoId);
    }

    public State findByNameAndCountryId(String stateName, Long countryId){
        return dao.findByNameAndCountryId(stateName, countryId);
    }
}
