package org.path.fortification.dao;

import org.path.fortification.entity.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Component
public class ProductDao extends BaseDao<Product>{
    private final EntityManager em;
    public ProductDao(EntityManager em) {
        super(em, Product.class);
        this.em = em;
    }

    public Optional<Product> findByName(String name) {
        try {
            return Optional.of(em.createQuery("FROM Product p where p.name = :name", Product.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException noResultException) {
            return Optional.ofNullable(null);
        }
    }
}