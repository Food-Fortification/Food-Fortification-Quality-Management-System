package org.path.iam.manager;

import org.path.iam.dao.CountryDao;
import org.path.iam.model.Country;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryManager extends BaseManager<Country, CountryDao>{
    private final CountryDao dao;
    public CountryManager(CountryDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<Country> findAll(String search, Integer pageNumber, Integer pageSize) {
        return dao.findAll(search, pageNumber, pageSize);
    }
    public Long getCount(String search){
        return  dao.getCount(search);
    }

    public Country findByName(String countryName){
        return dao.findByName(countryName);
    }
}
