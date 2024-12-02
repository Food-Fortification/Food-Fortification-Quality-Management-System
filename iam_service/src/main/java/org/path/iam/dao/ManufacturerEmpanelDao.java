package org.path.iam.dao;

import org.path.iam.model.ManufacturerEmpanel;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ManufacturerEmpanelDao extends BaseDao<ManufacturerEmpanel> {

    private final EntityManager em;
    public ManufacturerEmpanelDao(EntityManager em) {
        super(em, ManufacturerEmpanel.class);
        this.em = em;
    }

    public List<Long> getAllEmpanelledManufacturers(Long categoryId, String stateGeoId, Date fromDate, Date toDate) {
        try {
            String jpql = "SELECT DISTINCT m.manufacturerId FROM ManufacturerEmpanel m " +
                    "WHERE (:categoryId IS NULL OR m.categoryId = :categoryId) " +
                    "AND m.stateGeoId = :stateGeoId ";

            if (fromDate != null && toDate != null) {
                jpql += "AND (m.fromDate BETWEEN :fromDate AND :toDate OR m.toDate BETWEEN :fromDate AND :toDate)";
            }

            TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("stateGeoId", stateGeoId)
                    .setParameter("categoryId", categoryId);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate)
                        .setParameter("toDate", toDate);
            }

            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }


}
