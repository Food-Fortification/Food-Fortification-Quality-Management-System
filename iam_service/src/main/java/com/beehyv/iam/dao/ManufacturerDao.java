package com.beehyv.iam.dao;

import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.responseDto.DashboardCountResponseDto;
import com.beehyv.iam.dto.responseDto.ManufacturerAgencyResponseDto;
import com.beehyv.iam.enums.ManufacturerCategoryAction;
import com.beehyv.iam.enums.VendorType;
import com.beehyv.iam.model.Manufacturer;
import com.beehyv.iam.enums.ManufacturerType;
import com.beehyv.parent.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.*;

@Component
public class ManufacturerDao extends BaseDao<Manufacturer> {
    private final EntityManager em;
    public ManufacturerDao(EntityManager em) {
        super(em, Manufacturer.class);
        this.em = em;
    }

    public List<Manufacturer> findByIds(List<Long> ids) {
        try {
            return em
                    .createQuery("from Manufacturer where id in :ids  order by id asc", Manufacturer.class)
                    .setParameter("ids", ids).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
    public List<Manufacturer> findByIdAndType(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds, Integer pageNumber, Integer pageSize){
        try {
            String hql = "SELECT distinct(s) FROM Manufacturer m join m.targetManufacturers s " +
                    "join ManufacturerCategory mc on mc.manufacturer.id = s.id " +
                    "where m.id = :manufacturerId " +
                    "AND s.id != :manufacturerId " +
                    "AND mc.isEnabled is true " +
                    "AND s.manufacturerType = :type " +
                    "AND (:search is null or s.name like :search) " +
                    "AND (:targetCategoryIdsNull is true or mc.categoryId in (:targetCategoryIds)) " +
                    "AND mc.action =:action "+
                    "AND mc.isDeleted=:isDeleted "+
                    "order by s.id desc";
            TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class)
                    .setParameter("manufacturerId",manufacturerId)
                    .setParameter("action",ManufacturerCategoryAction.CREATION)
                    .setParameter("isDeleted", false)
                    .setParameter("type",type);
            this.setParameters(query, search, targetCategoryIds);
            if (pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber-1)*pageSize);
                query.setMaxResults(pageSize);
            }
            return query.getResultList();
        }catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
    public List<Manufacturer> findByTypeAndStatus(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds, Integer pageNumber, Integer pageSize){
        try {
            String hql = "SELECT distinct(m) FROM Manufacturer m " +
                    "join ManufacturerCategory mc on mc.manufacturer.id = m.id " +
                    "where m.manufacturerType = :type " +
                    "AND mc.isEnabled is true " +
                    "AND m.id != :manufacturerId " +
                    "AND (:search is null or m.name like :search) " +
                    "AND (:targetCategoryIdsNull is true or mc.categoryId in (:targetCategoryIds)) " +
                    "AND mc.action =:action "+
                    "AND mc.isDeleted=:isDeleted "+
                    "AND m.status.name = :status " +
                    "order by m.id desc";
            TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class)
                    .setParameter("type",type)
                    .setParameter("action",ManufacturerCategoryAction.CREATION)
                    .setParameter("isDeleted", false)
                    .setParameter("manufacturerId", manufacturerId)
                    .setParameter("status","Active");
            this.setParameters(query, search, targetCategoryIds);
            if (pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber-1)*pageSize);
                query.setMaxResults(pageSize);
            }
            return query.getResultList();
        }catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
    public Long getCountForTypeAndStatus(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds){
        try {
            String hql = "Select count(distinct(m).id) FROM Manufacturer m " +
                    "join ManufacturerCategory mc on mc.manufacturer.id = m.id " +
                    "where m.manufacturerType = :type " +
                    "AND mc.isEnabled is true " +
                    "AND m.id != :manufacturerId " +
                    "AND (:search is null or m.name like :search) " +
                    "AND (:targetCategoryIdsNull is true or mc.categoryId in (:targetCategoryIds)) " +
                    "AND mc.action =:action "+
                    "AND mc.isDeleted=:isDeleted "+
                    "AND m.status.name = :status";
            TypedQuery<Long> query = em.createQuery(hql, Long.class)
                    .setParameter("type",type)
                    .setParameter("action",ManufacturerCategoryAction.CREATION)
                    .setParameter("isDeleted", false)
                    .setParameter("manufacturerId", manufacturerId)
                    .setParameter("status","Active");
            this.setParameters(query, search, targetCategoryIds);
            return query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }
    public void setParameters(TypedQuery<?> query, String search, List<Long> targetCategoryIds){
        query.setParameter("targetCategoryIdsNull",true);
        query.setParameter("targetCategoryIds",targetCategoryIds);
        if (!targetCategoryIds.isEmpty()){
            query.setParameter("targetCategoryIdsNull",false);
        }
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
    }
    public Long getCountForIdAndType(Long manufacturerId, ManufacturerType type, String search, List<Long> targetCategoryIds){
        try {
            String hql = "SELECT count(distinct(s).id) FROM Manufacturer m join m.targetManufacturers s " +
                    "join ManufacturerCategory mc on mc.manufacturer.id = s.id " +
                    "where m.manufacturerType != 2 and  m.id = :manufacturerId " +
                    "AND s.id != :manufacturerId " +
                    "AND mc.isEnabled is true " +
                    "AND s.manufacturerType = :type " +
                    "AND (:search is null or s.name like :search) " +
                    "AND mc.action =:action "+
                    "AND mc.isDeleted=:isDeleted "+
                    "AND (:targetCategoryIdsNull is true or mc.categoryId in (:targetCategoryIds)) "
                    ;
            TypedQuery<Long> query = em.createQuery(hql, Long.class)
                    .setParameter("manufacturerId",manufacturerId)
                    .setParameter("action",ManufacturerCategoryAction.CREATION)
                    .setParameter("isDeleted", false)
                    .setParameter("type",type);
            this.setParameters(query, search, targetCategoryIds);
            return query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }
    public List<DashboardCountResponseDto> getDistrictCount(Long categoryId, String geoId){
        List<DashboardCountResponseDto> result = new ArrayList<>();
        boolean categoryIdNull = categoryId == null;
        String hql = "select count(*), m.address.village.district.geoId, count(distinct(m.id)) " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
                "and (:categoryIdNull is true or mc.categoryId = :categoryId) " +
                "and mc.action = :action " +
                "and m.address.village.district.state.geoId = :geoId " +
                "and m.status.name = :status group by m.address.village.district.geoId";

        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("categoryIdNull", categoryIdNull)
                .setParameter("categoryId", categoryId)
                .setParameter("status","Active")
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setId(String.valueOf(item[1]));
            dto.setTotalManufacturerCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[2]== null ? "0" : item[2].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<DashboardCountResponseDto> getEmpanelDistrictCount(Long categoryId, String geoId, List<Long> empanelledManufacturers){
        List<DashboardCountResponseDto> result = new ArrayList<>();

        String hql = "select count(*), m.address.village.district.geoId, count(distinct(m.id)) " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
//                "and mc.action = :action " +
                "and m.address.village.district.state.geoId = :geoId " +
                "and (:empanelledManufacturersNull is true or m.id in :empanelledManufacturers) " +
                "and m.status.name = :status group by m.address.village.district.geoId";

        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("status","Active")
                .setParameter("empanelledManufacturers", empanelledManufacturers)
                .setParameter("empanelledManufacturersNull", empanelledManufacturers==null)
//                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setId(String.valueOf(item[1]));
            dto.setTotalManufacturerCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[2]== null ? "0" : item[2].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<ManufacturerAgencyResponseDto> getManufacturerAgenciesByIds(Long categoryId, String filterBy, String geoId, List<Long> manufacturerIds){
        List<ManufacturerAgencyResponseDto> result = new ArrayList<>();
        boolean categoryIdNull = categoryId == null;
        String hql = "select m.id, m.address.village.district.geoId, m.agencyName, m.name, m.address.village.district.name " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
                "and (:categoryIdNull is true or mc.categoryId = :categoryId) " +
                "and mc.action = :action " +
                "and m.id in :mids " +
                "and m.address.village." + filterBy + ".geoId = :geoId " +
                "and m.status.name = :status group by m.address.village.district.geoId";

        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("categoryIdNull", categoryIdNull)
                .setParameter("categoryId", categoryId)
                .setParameter("status","Active")
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("mids", manufacturerIds == null ? new ArrayList<>() : manufacturerIds)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            ManufacturerAgencyResponseDto dto = new ManufacturerAgencyResponseDto();
            dto.setId((Long) item[0]);
            dto.setDistrictGeoId((String) item[1]);
            if(item[2] ==null) dto.setAgencyName((String) item[3]);
            else dto.setAgencyName((String) item[2]);
            dto.setDistrictName((String) item[4]);
            result.add(dto);
        }
        return result;
    }

    public List<DashboardCountResponseDto> getVillageCount(Long categoryId, String geoId){
        List<DashboardCountResponseDto> result = new ArrayList<>();
        boolean categoryIdNull = categoryId == null;
        String hql = "select count(*), count(distinct(m.id)) " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
                "and (:categoryIdNull is true or mc.categoryId = :categoryId) " +
                "and mc.action = :action " +
                "and m.address.village.district.geoId = :geoId " +
                "and m.status.name = :status group by m.address.village.id";

        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("categoryIdNull", categoryIdNull)
                .setParameter("categoryId", categoryId)
                .setParameter("status","Active")
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setTotalManufacturerCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[1]== null ? "0" : item[1].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<DashboardCountResponseDto> getEmapanelVillageCount(Long categoryId, String geoId, List<Long> empanelledManufacturers){
        List<DashboardCountResponseDto> result = new ArrayList<>();

        String hql = "select count(*), count(distinct(m.id)) " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
//                "and mc.action = :action " +
                "and m.address.village.district.geoId = :geoId " +
                "and (:empanelledManufacturersNull is true or m.id in :empanelledManufacturers) " +
                "and m.status.name = :status group by m.address.village.id";

        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("status","Active")
                .setParameter("empanelledManufacturers", empanelledManufacturers)
                .setParameter("empanelledManufacturersNull", empanelledManufacturers==null)
//                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setTotalManufacturerCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[1]== null ? "0" : item[1].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<DashboardCountResponseDto> getStateCount(Long categoryId, String geoId) {
        List<DashboardCountResponseDto> result = new ArrayList<>();
        boolean categoryIdNull = categoryId == null;
        String hql = "select count(*), m.address.village.district.state.geoId, count(distinct(m.id)) " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
                "and mc.action = :action " +
                "and (:categoryIdNull is true or mc.categoryId = :categoryId) " +
                "and m.address.village.district.state.country.geoId = :geoId " +
                "and m.status.name = :status group by m.address.village.district.state.geoId";
        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("categoryIdNull", categoryIdNull)
                .setParameter("categoryId", categoryId)
                .setParameter("status","Active")
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setId(String.valueOf(item[1]));
            dto.setTotalManufacturerCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[2]== null ? "0" : item[2].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<DashboardCountResponseDto> getEmpanelStateCount(Long categoryId, String geoId, List<Long> empanelledManufacturers) {
        List<DashboardCountResponseDto> result = new ArrayList<>();

        String hql = "select count(*), m.address.village.district.state.geoId, count(distinct(m.id)) " +
                "from Manufacturer m left join ManufacturerCategory mc on m.id = mc.manufacturer.id where m.manufacturerType != :manufacturerType " +
//                "and mc.action = :action " +
                "and (:empanelledManufacturersNull is true or m.id in :empanelledManufacturers) " +
                "and m.address.village.district.state.country.geoId = :geoId " +
                "and m.status.name = :status group by m.address.village.district.state.geoId";
        TypedQuery<Object[]> results = em.createQuery(hql, Object[].class)
                .setParameter("status","Active")
                .setParameter("empanelledManufacturers", empanelledManufacturers)
                .setParameter("empanelledManufacturersNull", empanelledManufacturers==null)
//                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("geoId", geoId)
                .setParameter("manufacturerType", ManufacturerType.MATERIAL);
        List<Object[]> objects = results.getResultList();
        for (Object[] item : objects){
            DashboardCountResponseDto dto = new DashboardCountResponseDto();
            dto.setId(String.valueOf(item[1]));
            dto.setTotalManufacturerCategories(Long.valueOf(item[0]== null ? "0" : item[0].toString()));
            dto.setTotal(Long.valueOf(item[2]== null ? "0" : item[2].toString()));
            result.add(dto);
        }
        return result;
    }

    public List<Manufacturer> findAllByDistrictGeoId(Long categoryId, String geoId, String search, Integer pageNumber, Integer pageSize) {
        try {
            TypedQuery<Manufacturer> query = em.createQuery("select distinct(m) from Manufacturer m inner join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                    "inner join Address a on a.id = m.address.id inner join Village v on v.id = a.village.id inner join District d on d.id = v.district.id " +
                    "where m.manufacturerType != 2 and mc.action = :action and (:categoryId is null or  mc.categoryId = :categoryId) and d.geoId = :geoId and (:search is null or m.name like :search) and m.status.name=:status order by m.name asc", Manufacturer.class)
                    .setParameter("categoryId", categoryId)
                    .setParameter("geoId", geoId)
                    .setParameter("action", ManufacturerCategoryAction.CREATION)
                    .setParameter("status","Active");
            query.setParameter("search",null);
            if (search != null && !Objects.equals(search, "")) {
                query.setParameter("search", "%" + search + "%");
            }
            if (pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber-1)*pageSize);
                query.setMaxResults(pageSize);
            }
            return query.getResultList();
        } catch (NoResultException e){
            return new ArrayList<>();
        }
    }
    public List<Manufacturer> findAllByStateGeoId(Long categoryId, String geoId, String search, Integer pageNumber, Integer pageSize) {
        try {
            TypedQuery<Manufacturer> query = em.createQuery("select distinct(m) from Manufacturer m inner join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                            "inner join Address a on a.id = m.address.id inner join Village v on v.id = a.village.id " +
                            "inner join District d on d.id = v.district.id inner join State s on s.id = d.state.id " +
                            "where m.manufacturerType != 2 and mc.action = :action and (:categoryId is null or mc.categoryId = :categoryId) and s.geoId = :geoId and (:search is null or m.name like :search) and m.status.name=:status  order by m.name asc", Manufacturer.class)
                    .setParameter("categoryId", categoryId)
                    .setParameter("geoId", geoId)
                    .setParameter("action", ManufacturerCategoryAction.CREATION)
                    .setParameter("status","Active");
            query.setParameter("search",null);
            if (search != null && !Objects.equals(search, "")) {
                query.setParameter("search", "%" + search + "%");
            }
            if (pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber-1)*pageSize);
                query.setMaxResults(pageSize);
            }
            return query.getResultList();
        } catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public Long findCountByDistrictGeoId(Long categoryId, String geoId, String search) {
        TypedQuery<Long> query = em.createQuery("select count(distinct(m).id) from Manufacturer m inner join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                        "inner join Address a on a.id = m.address.id inner join Village v on v.id = a.village.id inner join District d on d.id = v.district.id " +
                        "where m.manufacturerType != 2 and mc.action = :action and (:categoryId is null or  mc.categoryId = :categoryId) and d.geoId = :geoId and (:search is null or m.name like :search) and m.status.name=:status", Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("geoId", geoId)
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("status","Active");
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public Long findCountByStateGeoId(Long categoryId, String geoId, String search) {
        TypedQuery<Long> query = em.createQuery("select count(distinct(m).id) from Manufacturer m inner join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                        "inner join Address a on a.id = m.address.id inner join Village v on v.id = a.village.id " +
                        "inner join District d on d.id = v.district.id inner join State s on s.id = d.state.id " +
                        "where m.manufacturerType != 2 and mc.action = :action and (:categoryId is null or mc.categoryId = :categoryId) and s.geoId = :geoId and (:search is null or m.name like :search) and m.status.name=:status", Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("geoId", geoId)
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("status","Active");
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<Manufacturer> findAllByCountryGeoId(Long categoryId, String geoId, String search, Integer pageNumber, Integer pageSize) {
        try {
            TypedQuery<Manufacturer> query = em.createQuery("select distinct(m) from Manufacturer m inner join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                            "inner join Address a on a.id = m.address.id inner join Village v on v.id = a.village.id " +
                            "inner join District d on d.id = v.district.id inner join State s on s.id = d.state.id " +
                            "inner join Country c on c.id = s.country.id " +
                            "where m.manufacturerType != 2 and mc.action = :action and (:categoryId is null or mc.categoryId = :categoryId) and c.geoId = :geoId and (:search is null or m.name like :search) and m.status.name=:status", Manufacturer.class)
                    .setParameter("categoryId", categoryId)
                    .setParameter("geoId", geoId)
                    .setParameter("status","Active")
                    .setParameter("action", ManufacturerCategoryAction.CREATION);
            query.setParameter("search",null);
            if (search != null && !Objects.equals(search, "")) {
                query.setParameter("search", "%" + search + "%");
            }
            if (pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber-1)*pageSize);
                query.setMaxResults(pageSize);
            }
            return query.getResultList();
        } catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public Long findCountByCountryGeoId(Long categoryId, String geoId, String search) {
        TypedQuery<Long> query = em.createQuery("select count(distinct(m).id) from Manufacturer m inner join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                        "inner join Address a on a.id = m.address.id inner join Village v on v.id = a.village.id " +
                        "inner join District d on d.id = v.district.id inner join State s on s.id = d.state.id " +
                        "inner join Country c on c.id = s.country.id " +
                        "where m.manufacturerType != 2 and mc.action = :action and (:categoryId is null or mc.categoryId = :categoryId) and c.geoId = :geoId and (:search is null or m.name like :search) and m.status.name=:status", Long.class)
                .setParameter("categoryId", categoryId)
                .setParameter("geoId", geoId)
                .setParameter("action", ManufacturerCategoryAction.CREATION)
                .setParameter("status","Active");
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<Manufacturer>
    findAllManufacturers(Integer pageNumber, Integer pageSize,
        String search) {
        TypedQuery<Manufacturer> query = em.createQuery(
            "from Manufacturer m where m.manufacturerType != 2 and (:search is null or m.name like :search)",
            Manufacturer.class);
        query.setParameter("search", null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        if (pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getCountForAllManufacturers(String search) {
        TypedQuery<Long> query = em.createQuery(
            "select count(m.id) from Manufacturer m where m.manufacturerType != 2 and " +
                    " (:search is null or m.name like :search)",
            Long.class);
        query.setParameter("search",null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<Manufacturer> findByCategoryIds(String search, List<Long> categoryIds,
        Integer pageNumber, Integer pageSize) {
        try {
            String hql = "SELECT distinct(m) FROM Manufacturer m "
                + "join ManufacturerCategory mc on mc.manufacturer.id = m.id "
                + "where m.manufacturerType != 2 " +
                    "AND (:search is null or m.name like :search) " +
                    "AND (:categoryIdsNull is true or mc.categoryId in (:categoryIds)) " +
                    "AND mc.isDeleted is false " +
                    "and mc.isEnabled is true";

            TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class);

            query.setParameter("categoryIds",
                categoryIds.size() > 0 ? categoryIds : new ArrayList<>());
            query.setParameter("categoryIdsNull", categoryIds.size() <= 0);
            query.setParameter("search", null);
            if (search != null && !Objects.equals(search, "")) {
                query.setParameter("search", "%" + search + "%");
            }
            if (pageSize != null && pageNumber != null) {
                query.setFirstResult((pageNumber - 1) * pageSize);
                query.setMaxResults(pageSize);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public Long getCountForFindByCategoryIds(String search, List<Long> categoryIds) {
        String hql = "SELECT count(distinct(m).id) FROM Manufacturer m "
            + "join ManufacturerCategory mc on mc.manufacturer.id = m.id "
            + "where m.manufacturerType != 2 and " +
                "(:search is null or m.name like :search) " +
                "AND (:categoryIdsNull is true or mc.categoryId in (:categoryIds)) " +
                "AND mc.isDeleted is false " +
                "and mc.isEnabled is true";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        query.setParameter("categoryIds",
            categoryIds.size() > 0 ? categoryIds : new ArrayList<>());
        query.setParameter("categoryIdsNull", categoryIds.size() <= 0);
        query.setParameter("search", null);
        if (search != null && !Objects.equals(search, "")) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.getSingleResult();
    }

    public List<Manufacturer> getSourceManufacturers(Long manufacturerId, Long categoryId, String search, Integer pageNumber, Integer pageSize) {
        TypedQuery<Manufacturer> query = em.createQuery(
                "SELECT sm from Manufacturer m join m.sourceManufacturers sm join sm.manufacturerCategories smc " +
                   "where m.id = :manufacturerId and smc.categoryId = :categoryId and (:search is null or sm.name like :search)", Manufacturer.class)
                .setParameter("manufacturerId", manufacturerId)
                .setParameter("categoryId", categoryId)
                .setParameter("search", search != null ? ("%" + search + "%"): null);
        if(pageNumber != null && pageSize != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getSourceManufacturersCount(Long manufacturerId, Long categoryId, String search) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT count(sm) from Manufacturer m join m.sourceManufacturers sm join sm.manufacturerCategories smc " +
                   "where m.id = :manufacturerId and smc.categoryId =:categoryId and (:search is null or sm.name like :search)", Long.class)
                .setParameter("manufacturerId", manufacturerId)
                .setParameter("categoryId", categoryId)
                .setParameter("search", search != null ? ("%" + search + "%"): null);
        return query.getSingleResult();
    }

    public List<Long> getTestManufacturerIds() {
      try {
          return em.createQuery("select m.id from Manufacturer m where m.status.name = :status", Long.class)
                  .setParameter("status","Testing")
                  .getResultList();
      }catch (NoResultException e){
          return new ArrayList<>();
      }
    }

    public List<Manufacturer> getAllManufacturersWithGeoFilter(String search, Long stateId, Long districtId, Integer pageNumber, Integer pageSize) {
        String hql = "select m from Manufacturer m " +
                "where m.manufacturerType != 2 " +
                "and (:search is null or m.name like :search) " +
                "and (:stateId is null or m.address.village.district.state.id = :stateId) " +
                "and (:districtId is null or m.address.village.district.id = :districtId) " +
                "and m.status.name = :status ";
        TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class)
                .setParameter("stateId", stateId)
                .setParameter("districtId", districtId)
                .setParameter("status", "Active")
                .setParameter("search", search != null ? ("%" + search + "%"): null);
        if(pageNumber != null && pageSize != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public Long getAllManufacturersCountWithGeoFilter(String search, Long stateId, Long districtId) {
        String hql = "select count(m.id) from Manufacturer m " +
                "where m.manufacturerType != 2 " +
                "and (:search is null or m.name like :search) " +
                "and (:stateId is null or m.address.village.district.state.id = :stateId) " +
                "and (:districtId is null or m.address.village.district.id = :districtId) " +
                "and m.status.name = :status ";
        TypedQuery<Long> query = em.createQuery(hql, Long.class)
                .setParameter("stateId", stateId)
                .setParameter("districtId", districtId)
                .setParameter("status", "Active")
                .setParameter("search", search != null ? ("%" + search + "%"): null);
        return query.getSingleResult();
    }

    public List<Manufacturer> getAllManufacturersBySearchAndFilter(SearchListRequest searchRequest, Integer pageNumber, Integer pageSize) {
        String hql = "select distinct(m) from Manufacturer m " +
                "left join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                "where m.manufacturerType != 2 " +
                "and (:search is null or m.name like :search) " +
                "and (:stateIdsNull is true or m.address.village.district.state.id in :stateIds) " +
                "and (:districtIdsNull is true or m.address.village.district.id in :districtIds) " +
                "and (:categoriesNull is true or mc.categoryId in :categories) " +
                "and (:vendorType is null or m.vendorType = :vendorType ) " +
                "and (:manufacturerType is null or m.manufacturerType = :manufacturerType)" +
                "and m.status.name = :status " +
                "order by m.id asc";
        TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class);
        this.searchParams(query, searchRequest);
        if(pageNumber != null && pageSize != null) {
            query.setFirstResult((pageNumber-1)*pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    public List<Object[]> getManufacturerNamesByIds(List<Long> manufacturerIds){
        try {
            return em.createQuery("select m.id, m.name, m.address.village.district.geoId, m.address.village.district.name, m.address.village.district.state.name,m.licenseNumber, m.address.village.district.state.geoId  from Manufacturer m " +
                            "where m.id in :manufacturerIds", Object[].class)
                    .setParameter("manufacturerIds", manufacturerIds)
                    .getResultList();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public List<Object[]> getManufacturerNamesByIdsAndCategoryId(List<Long> manufacturerIds, Long categoryId){
        try {
            return em.createQuery("select distinct mc.manufacturer.id, " +
                            "mc.manufacturer.name, " +
                            "mc.manufacturer.address.village.district.name, " +
                            "mc.manufacturer.address.village.district.state.name " +
                            "from ManufacturerCategory mc " +
                            "where mc.manufacturer.id in :manufacturerIds " +
                            "and mc.action = :action " +
                            "and mc.categoryId = :categoryId ", Object[].class)
                    .setParameter("manufacturerIds", manufacturerIds)
                    .setParameter("action", ManufacturerCategoryAction.CREATION)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public Long getAllManufacturersCountBySearchAndFilter(SearchListRequest searchRequest) {
        String hql = "select count(distinct(m.id)) from Manufacturer m " +
                "left join ManufacturerCategory mc on m.id = mc.manufacturer.id " +
                "where m.manufacturerType != 2 " +
                "and (:search is null or m.name like :search) " +
                "and (:stateIdsNull is true or m.address.village.district.state.id in :stateIds) " +
                "and (:districtIdsNull is true or m.address.village.district.id in :districtIds) " +
                "and (:categoriesNull is true or mc.categoryId in :categories) " +
                "and (:vendorType is null or m.vendorType = :vendorType ) " +
                "and (:manufacturerType is null or m.manufacturerType = :manufacturerType)" +
                "and m.status.name = :status " +
                "order by m.id asc";
        TypedQuery<Long> query = em.createQuery(hql, Long.class);
        this.searchParams(query, searchRequest);
        return query.getSingleResult();
    }


    private void searchParams(TypedQuery<?> query, SearchListRequest searchRequest) {
        Map<String, Object> map = new HashMap<>();
        if(searchRequest != null && searchRequest.getSearchCriteriaList() != null) {
            searchRequest.getSearchCriteriaList()
                    .forEach(searchCriteria -> map.put(searchCriteria.getKey(), searchCriteria.getValue()));
        }
        query.setParameter("search",null);
        if (map.get("search") != null && !Objects.equals(map.get("search"), "")) {
            query.setParameter("search", "%" + map.get("search") + "%");
        }
        query.setParameter("status", "Active");
        if (map.get("status") != null && !Objects.equals(map.get("status"), "")) {
            query.setParameter("status",  map.get("search"));
        }
        query.setParameter("vendorType", null);
        try {
            if (map.get("vendorType") != null && !Objects.equals(map.get("vendorType"), "")) {
                query.setParameter("vendorType", VendorType.valueOf(map.get("vendorType").toString()));
            }
        }catch (Exception e){
            query.setParameter("vendorType", null);

        }
        query.setParameter("manufacturerType", null);
        try {
            if (map.get("manufacturerType") != null && !Objects.equals(map.get("manufacturerType"), "")) {
                query.setParameter("manufacturerType", ManufacturerType.valueOf(map.get("manufacturerType").toString()));
            }
        }catch (Exception e){
            query.setParameter("manufacturerType", null);

        }
        try {
            List<Long> categories = ((List<Integer>) map.get("categories")).stream()
                    .map(Integer::longValue).toList();
            if (categories.isEmpty())throw new CustomException("Categories not found", HttpStatus.BAD_REQUEST);
            query.setParameter("categories", categories);
            query.setParameter("categoriesNull", false);
        } catch (Exception e) {
            query.setParameter("categories", new ArrayList<>());
            query.setParameter("categoriesNull", true);
        }
        try {
            List<Long> categories = ((List<Integer>) map.get("states")).stream()
                    .map(Integer::longValue).toList();
            query.setParameter("stateIds", categories);
            query.setParameter("stateIdsNull", false);
        } catch (Exception e) {
            query.setParameter("stateIds", new ArrayList<>());
            query.setParameter("stateIdsNull", true);
        }
        try {
            List<Long> categories = ((List<Integer>) map.get("districts")).stream()
                    .map(Integer::longValue).toList();
            query.setParameter("districtIds", categories);
            query.setParameter("districtIdsNull", false);
        } catch (Exception e) {
            query.setParameter("districtIds", new ArrayList<>());
            query.setParameter("districtIdsNull", true);
        }
    }

    public Manufacturer findByExternalManufacturerId(String externalManufacturerId){
        try {
            return em.createQuery(
                    "SELECT m from Manufacturer m " +
                        "where m.externalManufacturerId = :externalManufacturerId ", Manufacturer.class)
                .setParameter("externalManufacturerId", externalManufacturerId)
                .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public Manufacturer findExternalManufacturerIdByManufacturerId(Long manufacturerId){
        try {
            return em.createQuery(
                            "SELECT m from Manufacturer m " +
                                    "where m.id = :manufacturerId ", Manufacturer.class)
                    .setParameter("manufacturerId", manufacturerId)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public List<Long> findManufacturerIdsByAgency(String agency){
        try {
            return em.createQuery(
                            "SELECT m.id from Manufacturer m " +
                                    "where m.agencyName = :agency or m.name = :agency ", Long.class)
                    .setParameter("agency", agency)
                    .getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public String findManufacturerNameById(Long manufacturerId) {
        try {
            return em.createQuery(
                            "SELECT m.name from Manufacturer m " +
                                    "where m.id = :manufacturerId ", String.class)
                    .setParameter("manufacturerId", manufacturerId)
                    .getSingleResult();
        }catch (NoResultException e){
            return "";
        }
    }

    public Manufacturer findByLicenseNo(String licenseNo) {
        try {
            return em.createQuery(
                            "SELECT m from Manufacturer m " +
                                    "where m.licenseNumber = :licenseNo ", Manufacturer.class)
                    .setParameter("licenseNo", licenseNo)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public Manufacturer findByLicenseNoAndCategoryId(String licenseNo, Long categoryId){
        try {
            String hql = "SELECT m FROM Manufacturer m " +
                    "join ManufacturerCategory mc on mc.manufacturer.id = m.id " +
                    "where mc.isEnabled is true " +
                    "and mc.categoryId = :categoryId " +
                    "and mc.isDeleted is not true " +
                    "and m.licenseNumber = :licenseNo";
            TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class)
                    .setParameter("licenseNo",licenseNo)
                    .setParameter("categoryId",categoryId);

            return query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    public Manufacturer findByLicenseNoAndCategoryId(String licenseNo, List<Long> targetCategoryIds){
        try {
            String hql = "SELECT m FROM Manufacturer m " +
                    "join ManufacturerCategory mc on mc.manufacturer.id = m.id " +
                    "where mc.isEnabled is true " +
                    "and mc.categoryId in :targetCategoryIds " +
                    "and m.licenseNumber = :licenseNo";
            TypedQuery<Manufacturer> query = em.createQuery(hql, Manufacturer.class)
                    .setParameter("licenseNo",licenseNo)
                    .setParameter("targetCategoryIds",targetCategoryIds);

            return query.getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    public List<Manufacturer> findManufacturers(Long start, Long end){
        return em.createQuery(
                        "SELECT m from Manufacturer m " +
                                "where m.id >= :start and m.id <= :end ", Manufacturer.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public String findLicenseNumberById(Long id) {
        try {
            return em.createQuery(
                            "SELECT m.licenseNumber from Manufacturer m " +
                                    "where m.id = :id", String.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public String findLicenseNumberByName(String name) {
        try {
            return em.createQuery(
                            "SELECT m.licenseNumber from Manufacturer m " +
                                    "where m.name = :name", String.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<String>getAllManufacturerLicenseNos(){
        try{
            return em.createQuery(
                            "SELECT m.licenseNumber from Manufacturer m " +
                                    "where m.isDeleted is false", String.class)
                    .getResultList();
        }catch (NoResultException e){
            return null;
        }
    }
}
