package org.path.iam.dao;

import org.path.iam.model.Address;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class AddressDao extends BaseDao<Address>{
    private final EntityManager em;
    public AddressDao(EntityManager em) {
        super(em, Address.class);
        this.em = em;
    }
    public Address findByManufacturerId(Long manufacturerId){
        return em.createQuery("FROM Address a WHERE a.manufacturer.id = :manufacturerId",Address.class)
                .setParameter("manufacturerId",manufacturerId)
                .getSingleResult();
    }
}
