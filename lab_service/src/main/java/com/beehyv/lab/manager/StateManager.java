package com.beehyv.lab.manager;

import com.beehyv.lab.dao.StateDao;
import com.beehyv.lab.entity.State;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StateManager extends BaseManager<State, StateDao>{
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
}

