package org.path.iam.dao;

import org.path.iam.dto.requestDto.ManufacturerDispatchableQuantityRequestDto;
import org.path.iam.dto.responseDto.ManufacturerDispatchableQuantityResponseDto;
import org.path.iam.model.PurchaseOrder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PurchaseOrderDao extends BaseDao<PurchaseOrder> {

    private final EntityManager em;

    public PurchaseOrderDao(EntityManager em) {
        super(em, PurchaseOrder.class);
        this.em = em;
    }

    public List<PurchaseOrder> findBySourceTargetMapping(Long sourceManufacturerId, Long targetManufacturerId) {
        try {
            return em.createQuery(
                            "SELECT po from PurchaseOrder po " +
                                    "where po.sourceManufacturerId = :sourceManufacturerId AND po.targetManufacturerId = :targetManufacturerId", PurchaseOrder.class)
                    .setParameter("sourceManufacturerId", sourceManufacturerId)
                    .setParameter("targetManufacturerId", targetManufacturerId)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public Boolean findExistingPurchaseOrder(String purchaseOrderId) {
        try {
            List<PurchaseOrder> purchaseOrderList = em.createQuery(
                            "SELECT po from PurchaseOrder po " +
                                    "where po.purchaseOrderId = :purchaseOrderId", PurchaseOrder.class)
                    .setParameter("purchaseOrderId", purchaseOrderId)
                    .getResultList();
            return !purchaseOrderList.isEmpty();
        } catch (NoResultException e) {
            return false;
        }
    }

    public PurchaseOrder findByPurchaseOrderIds(String purchaseOrderId, String childPurchaseOrderId) {
        try {
            return em.createQuery(
                            "SELECT po from PurchaseOrder po " +
                                    "where po.purchaseOrderId = :purchaseOrderId and po.childPurchaseOrderId = :childPurchaseOrderId", PurchaseOrder.class)
                    .setParameter("purchaseOrderId", purchaseOrderId)
                    .setParameter("childPurchaseOrderId", childPurchaseOrderId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<ManufacturerDispatchableQuantityResponseDto> getManufacturerDispatchableQuantityAggregate(ManufacturerDispatchableQuantityRequestDto dto) {
        TypedQuery<ManufacturerDispatchableQuantityResponseDto> query = em.createQuery(
                        "SELECT NEW responseDto.dto.org.path.iam.ManufacturerDispatchableQuantityResponseDto(p.sourceManufacturerId as id, sum(p.maxDispatchableQuantity) as demand, sum(p.dispatchedQuantity) as supply) " +
                                "from PurchaseOrder p " +
                                "where (:dateIsNull is true or (p.dmOrderDate <= :toDate and p.dmOrderDate >= :fromDate)) " +
                                "and (:manufacturerIdsNull is true or p.sourceManufacturerId in :manufacturerIds) " +
                                "group by p.sourceManufacturerId", ManufacturerDispatchableQuantityResponseDto.class)
                .setParameter("manufacturerIds", dto.getManufacturerIds())
                .setParameter("manufacturerIdsNull", dto.getManufacturerIds() == null)
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateIsNull", false);

        if (dto.getFromDate() == null) {
            query.setParameter("dateIsNull", true);
        }
        return query.getResultList();
    }

    public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByDistrictGeoId(ManufacturerDispatchableQuantityRequestDto dto) {
        String groupBy = "sourceManufacturerId";
        if ("district".equals(dto.getGroupBy())) {
            groupBy = "targetDistrictGeoId";
        }
        TypedQuery<ManufacturerDispatchableQuantityResponseDto> query = em.createQuery(
                        "SELECT NEW responseDto.dto.org.path.iam.ManufacturerDispatchableQuantityResponseDto(p.sourceManufacturerId as id, " +
                                "sum(p.maxDispatchableQuantity) as demand, " +
                                "sum(p.dispatchedQuantity) as supply, " +
                                "p.targetDistrictGeoId as districtGeoId ) " +
                                "from PurchaseOrder p " +
                                "where (:dateIsNull is true or (p.dmOrderDate <= :toDate and p.dmOrderDate >= :fromDate)) " +
                                "and (:districtGeoIdsNull is true or p.targetDistrictGeoId in :districtGeoIds) " +
                                "and :sourceManufacturerIdNull is true or p.sourceManufacturerId = :sourceManufacturerId " +
                                "group by p." + groupBy, ManufacturerDispatchableQuantityResponseDto.class)
                .setParameter("districtGeoIds", dto.getDistrictGeoIds())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateIsNull", dto.getFromDate() == null)
                .setParameter("districtGeoIdsNull", dto.getDistrictGeoIds() == null)
                .setParameter("sourceManufacturerId", dto.getSourceManufacturerId())
                .setParameter("sourceManufacturerIdNull", dto.getSourceManufacturerId() == null);

        return query.getResultList();
    }

    public List<ManufacturerDispatchableQuantityResponseDto> getDispatchableQuantityByDistrictGeoIdForTarget(ManufacturerDispatchableQuantityRequestDto dto) {
        TypedQuery<ManufacturerDispatchableQuantityResponseDto> query = em.createQuery(
                        "SELECT NEW responseDto.dto.org.path.iam.ManufacturerDispatchableQuantityResponseDto(p.targetManufacturerId as id, " +
                                "sum(p.maxDispatchableQuantity) as demand, " +
                                "sum(p.dispatchedQuantity) as supply, " +
                                "p.targetDistrictGeoId as districtGeoId ) " +
                                "from PurchaseOrder p " +
                                "where (:dateIsNull is true or (p.dmOrderDate <= :toDate and p.dmOrderDate >= :fromDate)) " +
                                "and (:districtGeoIdsNull is true or p.targetDistrictGeoId in :districtGeoIds) " +
                                "and (:sourceManufacturerId is null or p.sourceManufacturerId = :sourceManufacturerId) " +
                                "group by p.targetManufacturerId", ManufacturerDispatchableQuantityResponseDto.class)
                .setParameter("districtGeoIds", dto.getDistrictGeoIds())
                .setParameter("fromDate", dto.getFromDate())
                .setParameter("toDate", dto.getToDate() == null ? new Date() : dto.getToDate())
                .setParameter("dateIsNull", dto.getFromDate() == null)
                .setParameter("districtGeoIdsNull", dto.getDistrictGeoIds() == null)
                .setParameter("sourceManufacturerId", dto.getSourceManufacturerId());
        return query.getResultList();
    }
}
