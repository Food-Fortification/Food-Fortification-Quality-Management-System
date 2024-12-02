package org.path.iam.dao;


import org.path.iam.enums.ManufacturerCategoryAction;
import org.path.iam.model.ManufacturerCategory;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class ManufacturerCategoryDao extends BaseDao<ManufacturerCategory> {
    private final EntityManager em;
    public ManufacturerCategoryDao(EntityManager em) {
        super(em, ManufacturerCategory.class);
        this.em = em;
    }
    public List<ManufacturerCategory> getByCategoryId(Long categoryId){
        TypedQuery<ManufacturerCategory> query = em
                .createQuery("from ManufacturerCategory b where b.categoryId = :categoryId and b.isEnabled is true", ManufacturerCategory.class)
                .setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    public List<ManufacturerCategory> findAllByManufacturerId(Long manufacturerId) {
        return em.createQuery("from ManufacturerCategory b where b.manufacturer.id = :manufacturerId and b.isEnabled is true",ManufacturerCategory.class)
                .setParameter("manufacturerId",manufacturerId)
                .getResultList();
    }

    public Boolean getCanSkipRawMaterialsForManufacturerAndCategory(Long manufacturerId, Long categoryId) {
        try {
            return em.createQuery("select mc.canSkipRawMaterials from ManufacturerCategory mc " +
                            "where mc.manufacturer.id = :manufactuerId and mc.categoryId = :categoryId", Boolean.class)
                    .setParameter("manufactuerId", manufacturerId)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
        }catch (NoResultException ex){
            return false;
        }

    }

    public ManufacturerCategoryAction getActionNameByManufacturerIdAndCategoryId(Long manufacturerId, Long categoryId) {
        try {
            return em.createQuery("select mc.action from ManufacturerCategory mc " +
                            "where mc.manufacturer.id = :manufactuerId and mc.categoryId = :categoryId", ManufacturerCategoryAction.class)
                    .setParameter("manufactuerId", manufacturerId)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
        }catch (NoResultException ex){return null ;}

    }


    public List<Long> filterManufacturersByCategory(Long categoryId, List<Long> manufacturerIds){
        return em.createQuery("select distinct mc.manufacturer.id from ManufacturerCategory mc " +
                        "where mc.manufacturer.id in :manufacturerIds and mc.categoryId = :categoryId", Long.class)
                .setParameter("manufacturerIds", manufacturerIds)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
}
