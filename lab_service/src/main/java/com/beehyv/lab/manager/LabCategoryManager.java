package com.beehyv.lab.manager;

import com.beehyv.lab.dao.LabCategoryDao;
import com.beehyv.lab.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.lab.entity.LabCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabCategoryManager extends BaseManager<LabCategory, LabCategoryDao>{

    private LabCategoryDao dao;

    public LabCategoryManager(LabCategoryDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<LabCategory> findAllByLabId(Long labId) {
        return dao.findAllByLabId(labId);
    }

    public List<LabCategory> findNearestLab(AddressLocationResponseDto addressLocationResponseDto, Long categoryId){
        String manufacturerState = addressLocationResponseDto.getState().getName();
        String manufacturerCountry = addressLocationResponseDto.getCountry().getName();

        return dao.findByCountryAndState(manufacturerCountry, manufacturerState, categoryId);
    }
}
