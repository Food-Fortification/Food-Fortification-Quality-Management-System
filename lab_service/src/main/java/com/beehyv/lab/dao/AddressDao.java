package com.beehyv.lab.dao;

import com.beehyv.lab.entity.Address;
import com.beehyv.lab.entity.Country;
import com.beehyv.lab.entity.Village;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class AddressDao extends BaseDao<Address>{

    public AddressDao(EntityManager em) {
        super(em, Address.class);
    }
}
