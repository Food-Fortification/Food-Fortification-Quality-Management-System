package com.beehyv.lab.manager;

import com.beehyv.lab.dto.requestDto.DashboardRequestDto;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.AddressLocationResponseDto;
import com.beehyv.lab.dto.responseDto.DashboardCountResponseDto;
import com.beehyv.lab.entity.*;
import com.beehyv.lab.dao.LabDao;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LabManager extends BaseManager<Lab, LabDao>{

    private final LabDao dao;
    public LabManager(LabDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<Lab> findNearestLab(AddressLocationResponseDto addressLocationResponseDto){
        String manufacturerState = addressLocationResponseDto.getState().getName();
        String manufacturerCountry = addressLocationResponseDto.getCountry().getName();

        return dao.findByCountryAndState(manufacturerCountry,manufacturerState);
    }

    public List<Lab> findByPinCode(String  pinCode){
        return dao.findByPinCode(pinCode);
    }

    public Map<Long, Lab> findAllByIds(Set<Long> ids) {
        return dao.findAllByIds(ids.stream().toList()).stream().collect(Collectors.toMap(Lab::getId, d -> d));
    }

    public Map<Long, Lab> findAllByCategoryIdAndGeoId(Long categoryId, String filterBy, String geoId, Integer pageNumber, Integer pageSize) {
        List<Lab> results = dao.findAllByCategoryIdAndGeoId(categoryId, filterBy, geoId, pageNumber, pageSize);
        return results.stream().collect(Collectors.toMap(Lab::getId, d -> d));
    }

    public List<Lab> findAllLabs(SearchListRequest searchListRequest, String search, Integer pageNumber, Integer pageSize){
        return dao.findAllLabs(searchListRequest, search, pageNumber, pageSize);
    }
    public Long getCountForAllLabs(SearchListRequest searchListRequest, String search){
        return dao.getCountForAllLabs(searchListRequest, search);
    }

    public List<Long> getAllActiveLabIds(){
        return dao.getAllActiveLabIds();
    }

    public List<Lab> findAllActiveLabsForCategory(String search, Long categoryId, Integer pageNumber, Integer pageSize) {
        return dao.findAllActiveLabsForCategory(search, categoryId, pageNumber,pageSize);
    }

    public Long getCountForAllActiveLabsCategory(String search, Long categoryId){
        return dao.getCountForAllActiveLabsCategory(search, categoryId);
    }

    public List<DashboardCountResponseDto> getDistrictCount(Long categoryId, String geoId){
        return dao.getDistrictCount(categoryId, geoId);
    }

    public List<DashboardCountResponseDto> getStateCount(Long categoryId, String geoId) {
        return dao.getStateCount(categoryId, geoId);
    }

    public List<Object[]> getLabsAggregateByCategoryId(DashboardRequestDto dto){
        return dao.getLabsAggregateByCategoryId(dto);
    }

    public List<LabSample> getLabSamplesDetails(DashboardRequestDto dto, Long labId, List stateNames){
        return dao.getLabSampleDetails(dto, labId, stateNames);
    }
    public String findCertificateByName(String name){
        return dao.findCertificateByName(name);
    }
}
