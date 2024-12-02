package org.path.lab.dao;

import org.path.lab.dto.responseDto.SampleStateCountResponseDto;
import org.path.lab.entity.SampleStateGeo;
import org.path.lab.entity.SampleStateGeoId;
import java.util.Objects;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class SampleStateGeoDao extends BaseDao<SampleStateGeo>{
    private final EntityManager em;
    public SampleStateGeoDao(EntityManager em) {
        super(em, SampleStateGeo.class);
        this.em = em;
    }

    public Long[] getSamplesGeoCount(Long categoryId, String filterBy, String geoId, List<Long> activeLabIds, Integer year) {
        Object object = em.createQuery(
                        "select " +
                                " sum(s.testedCount)," +
                                " sum(s.inTransitCount), " +
                                " sum(s.underTestingCount), " +
                                " sum(s.rejectedCount) " +
                                "from SampleStateGeo s " +
                                "where (:categoryId is null or s.stateGeoId.categoryId = :categoryId) " +
                                "and s.stateGeoId.sampleSentYear = :year " +
                                "and (s.stateGeoId.labId IN :activeLabIds) " +
                                "and (:filterBy is null or s.stateGeoId."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) ")
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("activeLabIds",activeLabIds)
                .getSingleResult();
        Long[] results = new Long[4];
        results[0] = (Long) (((Object[]) object)[0]);
        results[1] = (Long) (((Object[]) object)[1]);
        results[2] = (Long) (((Object[]) object)[2]);
        results[3] = (Long) (((Object[]) object)[3]);
        return results;
    }


    public List<SampleStateCountResponseDto> getAggregatedGeoSamplesCount(Long categoryId, String groupBy,
                                                                          String filterBy, String geoId, List<Long> activeLabIds,
                                                                          Integer year) {
        List object = em.createQuery(
                        "select s.stateGeoId."+groupBy+" ," +
                                " sum(s.testedCount)," +
                                " sum(s.inTransitCount), " +
                                " sum(s.underTestingCount), " +
                                " sum(s.rejectedCount) " +
                                "from SampleStateGeo s " +
                                "where (:categoryId is null or s.stateGeoId.categoryId = :categoryId) " +
                                "and (s.stateGeoId.labId in (:activeLabIds)) " +
                                "and s.stateGeoId.sampleSentYear = :year " +
                                "and (:filterBy is null or s.stateGeoId." +
                                Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                                "group by s.stateGeoId." + groupBy)
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("activeLabIds", activeLabIds)
                .getResultList();
        return (List<SampleStateCountResponseDto>) object.stream().map(d -> {
            Object[] resp = (Object[]) d;
            SampleStateCountResponseDto responseDto = new SampleStateCountResponseDto();
            responseDto.setId((String) resp[0]);
            responseDto.setTestedCount((Long) resp[1]);
            responseDto.setInTransitCount((Long) resp[2]);
            responseDto.setUnderTestingCount((Long) resp[3]);
            responseDto.setRejectedCount((Long) resp[4]);
            return responseDto;
        }).toList();
    }

    public List<SampleStateGeo> findAllByCategoryIdAndGeoId(Long categoryId, String filterBy, String geoId, Set<Long> activeLabIds,
                                                            Integer year, Integer pageNumber, Integer pageSize, String search) {
        TypedQuery<SampleStateGeo> query = em.createQuery(
                "Select b FROM SampleStateGeo b " +
                    "LEFT JOIN Lab l " +
                    "ON b.stateGeoId.labId = l.id " +
                    "WHERE (:categoryId is null or b.stateGeoId.categoryId = :categoryId) " +
                    "and (b.stateGeoId.labId in (:activeLabIds)) " +
                    "and (:search is null or l.name like :search) " +
                    "and b.stateGeoId.sampleSentYear = :year " +
                    "and (:filterBy is null or b.stateGeoId."
                    + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) " +
                    "order by b.testedCount desc "
                ,
                SampleStateGeo.class
            )
            .setParameter("categoryId", categoryId)
            .setParameter("filterBy", filterBy)
            .setParameter("activeLabIds", activeLabIds)
            .setParameter("year", year)
            .setParameter("search",null)
            .setParameter("geoId", geoId);
        if(pageSize != null && pageNumber != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        if (search != null && !Objects.equals(search, "")){
            query.setParameter("search", "%"+search+"%");
        }
        return query.getResultList();
    }

    public Long findAllByCategoryIdAndGeoIdCount(Long categoryId, String filterBy, String geoId, Integer year, Set<Long> activeLabIds, String search) {
        TypedQuery<Long> query = em.createQuery(
                        "select count(b) FROM SampleStateGeo b " +
                                "LEFT JOIN Lab l " +
                                "ON b.stateGeoId.labId = l.id " +
                                "WHERE (:categoryId is null or b.stateGeoId.categoryId = :categoryId) " +
                                "and b.stateGeoId.sampleSentYear = :year " +
                                "and (b.stateGeoId.labId in (:activeLabIds)) " +
                                "and (:search is null or l.name like :search) " +
                                "and b.stateGeoId.sampleSentYear = :year " +
                                "and (:filterBy is null or b.stateGeoId."
                                + Optional.ofNullable(filterBy).orElse("countryGeoId") + " = :geoId) "
                        ,
                        Long.class
                )
                .setParameter("categoryId", categoryId)
                .setParameter("filterBy", filterBy)
                .setParameter("geoId", geoId)
                .setParameter("year", year)
                .setParameter("activeLabIds", activeLabIds)
                .setParameter("search",null);
        if (search != null && !Objects.equals(search, "")){
            query.setParameter("search", "%"+search+"%");
        }
        return query.getSingleResult();
    }

    public SampleStateGeo findByCategoryIdAndLabIdAndGeoIds(SampleStateGeoId sampleStateGeoId) {
        return em.createQuery(
                "from SampleStateGeo s " +
                        "where s.stateGeoId = :sampleStateGeoId"
                        ,
                SampleStateGeo.class
        )
                .setParameter("sampleStateGeoId", sampleStateGeoId)
                .getSingleResult();
    }
}
