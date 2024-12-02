package org.path.lab.dao;

import org.path.lab.entity.Address;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class AddressDao extends BaseDao<Address>{

    public AddressDao(EntityManager em) {
        super(em, Address.class);
    }
}
