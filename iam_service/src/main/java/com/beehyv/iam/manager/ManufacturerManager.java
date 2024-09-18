package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerDao;
import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.responseDto.DashboardCountResponseDto;
import com.beehyv.iam.dto.responseDto.ManufacturerAgencyResponseDto;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.enums.ManufacturerType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManufacturerManager extends BaseManager<Manufacturer, ManufacturerDao> {
    private final ManufacturerDao dao;
    public ManufacturerManager(ManufacturerDao dao) {
        super(dao);
        this.dao = dao;
    }

    public List<Manufacturer> findByIds(List<Long> ids) {
        return dao.findByIds(ids);
    }

    public List<Manufacturer> findByIdAndType(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds, Integer pageNumber, Integer pageSize) {
        return dao.findByIdAndType(manufacturerId, type, search, targetCategoryIds, pageNumber, pageSize);
    }
    public List<Manufacturer> findByTypeAndStatus(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds, Integer pageNumber, Integer pageSize){
        return dao.findByTypeAndStatus(manufacturerId, type, search, targetCategoryIds, pageNumber, pageSize);
    }
    public Long getCountForIdAndType(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds){
        return dao.getCountForIdAndType(manufacturerId, type, search, targetCategoryIds);
    }
    public Long getCountForTypeAndStatus(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds){
        return dao.getCountForTypeAndStatus(manufacturerId, type, search, targetCategoryIds);
    }
    public List<DashboardCountResponseDto> getDistrictCount(Long categoryId, String geoId){
        return dao.getDistrictCount(categoryId, geoId);
    }

    public List<DashboardCountResponseDto> getEmpanelDistrictCount(Long categoryId, String geoId, List<Long> empanelledManufacturers){
        return dao.getEmpanelDistrictCount(categoryId, geoId, empanelledManufacturers);
    }

    public List<ManufacturerAgencyResponseDto> getManufacturerAgenciesByIds(Long categoryId, String filterBy, String geoId, List<Long> manufacturerIds) {
        return dao.getManufacturerAgenciesByIds(categoryId, filterBy, geoId, manufacturerIds);
    }


    public List<DashboardCountResponseDto> getVillageCount(Long categoryId, String geoId){
        return dao.getVillageCount(categoryId, geoId);
    }

    public List<DashboardCountResponseDto> getEmpanelVillageCount(Long categoryId, String geoId, List<Long> empanelledManufacturers){
        return dao.getEmapanelVillageCount(categoryId, geoId, empanelledManufacturers);
    }

    public List<DashboardCountResponseDto> getStateCount(Long categoryId, String geoId) {
        return dao.getStateCount(categoryId, geoId);
    }

    public List<DashboardCountResponseDto> getEmpanelStateCount(Long categoryId, String geoId, List<Long> empanelledManufacturers) {
        return dao.getEmpanelStateCount(categoryId, geoId,empanelledManufacturers);
    }

    public List<Manufacturer> findAllByDistrictGeoId(Long categoryId, String geoId, String search, Integer pageNumber, Integer pageSize) {
        return dao.findAllByDistrictGeoId(categoryId, geoId, search, pageNumber, pageSize);
    }
    public Long findCountByDistrictGeoId(Long categoryId, String geoId, String search) {
        return dao.findCountByDistrictGeoId(categoryId, geoId, search);
    }
    public List<Manufacturer> findAllByStateGeoId(Long categoryId, String geoId, String search, Integer pageNumber, Integer pageSize) {
        return dao.findAllByStateGeoId(categoryId, geoId, search, pageNumber, pageSize);
    }
    public Long findCountByStateGeoId(Long categoryId, String geoId, String search) {
        return dao.findCountByStateGeoId(categoryId, geoId, search);
    }

    public List<Manufacturer> findAllByCountryGeoId(Long categoryId, String geoId, String search, Integer pageNumber, Integer pageSize) {
        return dao.findAllByCountryGeoId(categoryId, geoId, search, pageNumber, pageSize);
    }

    public Long findCountByCountryGeoId(Long categoryId, String geoId, String search) {
        return dao.findCountByCountryGeoId(categoryId, geoId, search);
    }

    public List<Manufacturer> findAllManufacturers(String search, Integer pageNumber, Integer pageSize){
        return dao.findAllManufacturers(pageNumber,pageSize,search);
    }

    public Long getCountForAllManufacturers(String search){
        return dao.getCountForAllManufacturers(search);
    }

    public List<Manufacturer> findByCategoryIds(String search, List<Long> categoryIds,
        Integer pageNumber, Integer pageSize) {
        return dao.findByCategoryIds(search, categoryIds, pageNumber, pageSize);
    }

    public Long getCountForFindByCategoryIds(String search, List<Long> categoryIds){
        return dao.getCountForFindByCategoryIds(search, categoryIds);
    }

    public List<Manufacturer> getSourceManufacturers(Long manufacturerId, Long categoryId, String search, Integer pageNumber, Integer pageSize) {
        return dao.getSourceManufacturers(manufacturerId, categoryId, search, pageNumber, pageSize);
    }

    public Long getSourceManufacturersCount(Long manufacturerId, Long categoryId, String search) {
        return dao.getSourceManufacturersCount(manufacturerId, categoryId, search);
    }

    public List<Long> getTestManufacturerIds() {
        return dao.getTestManufacturerIds();
    }

    public List<Manufacturer> getAllManufacturersWithGeoFilter(String search, Long stateId, Long districtId, Integer pageNumber, Integer pageSize) {
        return dao.getAllManufacturersWithGeoFilter(search, stateId, districtId, pageNumber, pageSize);
    }

    public Long getAllManufacturersCountWithGeoFilter(String search, Long stateId, Long districtId) {
        return dao.getAllManufacturersCountWithGeoFilter(search, stateId, districtId);
    }

    public List<Manufacturer> getAllManufacturersBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize) {
        return dao.getAllManufacturersBySearchAndFilter(searchRequest, pageNumber, pageSize);
    }

    public List<Object[]> getManufacturerNamesByIds(List<Long> manufacturerIds){
        return dao.getManufacturerNamesByIds(manufacturerIds);
    }

    public List<Object[]> getManufacturerNamesByIdsAndCategoryId(List<Long> manufacturerIds, Long categoryId){
        return dao.getManufacturerNamesByIdsAndCategoryId(manufacturerIds, categoryId);
    }

    public Long getAllManufacturersCountBySearchAndFilter(SearchListRequest searchRequest) {
        return dao.getAllManufacturersCountBySearchAndFilter(searchRequest);
    }

    public Manufacturer findByExternalManufacturerId(String externalManufacturerId){
        return dao.findByExternalManufacturerId(externalManufacturerId);
    }

    public Manufacturer findByLicenseNo(String licenseNo){
        return dao.findByLicenseNo(licenseNo);
    }

    public Manufacturer findByLicenseNoAndCategoryId(String licenseNo, Long categoryId){
        return dao.findByLicenseNoAndCategoryId(licenseNo, categoryId);
    }

    public Manufacturer findByLicenseNoAndCategoryId(String licenseNo, List<Long> targetCategoryIds){
        return dao.findByLicenseNoAndCategoryId(licenseNo, targetCategoryIds);
    }

    public Manufacturer findExternalManufacturerIdByManufacturerId(Long manufacturerId){
        return dao.findExternalManufacturerIdByManufacturerId(manufacturerId);
    }

    public List<Long> findManufacturerIdsByAgency(String agency){
        return dao.findManufacturerIdsByAgency(agency);
    }

    public String findManufacturerNameById(Long manufacturerId) {
        return dao.findManufacturerNameById(manufacturerId);
    }

    public List<Manufacturer> findManufacturers(Long start, Long end){
        return dao.findManufacturers(start,end);
    }

    public String findLicenseNumberById(Long id){
        return dao.findLicenseNumberById(id);
    }

    public String findLicenseNumberByName(String name){
        return dao.findLicenseNumberByName(name);
    }

    public List<String> getAllManufacturerLicenseNos(){
        return dao.getAllManufacturerLicenseNos();
    }
}
