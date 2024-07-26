package com.beehyv.iam.manager;

import com.beehyv.iam.dao.AddressDao;
import com.beehyv.iam.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressManager extends BaseManager<Address, AddressDao>{
    private final AddressDao dao;
    public AddressManager(AddressDao dao) {
        super(dao);
        this.dao=dao;
    }
    public Address findByManufacturerId(Long manufacturerId){
        return dao.findByManufacturerId(manufacturerId);
    }
}
