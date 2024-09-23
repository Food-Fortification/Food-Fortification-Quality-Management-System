package com.beehyv.iam.dao;

import com.beehyv.iam.model.ManufacturerCategoryAttributes;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ManufacturerCategoryAttributesDao extends BaseDao<ManufacturerCategoryAttributes> {

  private final EntityManager em;
  public ManufacturerCategoryAttributesDao(EntityManager em) {
    super(em, ManufacturerCategoryAttributes.class);
    this.em = em;
  }

    public List<ManufacturerCategoryAttributes> findByManufacturerId(Long manufacturerId) {
      try {
        return em.createQuery("from ManufacturerCategoryAttributes m where m.manufacturer.id = :manufacturerId", ManufacturerCategoryAttributes.class)
                .setParameter("manufacturerId", manufacturerId)
                .getResultList();
      }catch (NoResultException e){
        return new ArrayList<>();
      }
    }

  public List<ManufacturerCategoryAttributes> findByManufacturerIdAndUserId(Long manufacturerId, Long userId) {
    try {
      return em.createQuery("from ManufacturerCategoryAttributes m where m.manufacturer.id = :manufacturerId" +
                      " and m.user.id = :userId", ManufacturerCategoryAttributes.class)
              .setParameter("manufacturerId", manufacturerId)
              .setParameter("userId", userId)
              .getResultList();
    }catch (NoResultException e){
      return new ArrayList<>();
    }
  }
}
