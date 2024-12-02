package org.path.iam.manager;

import org.path.iam.dao.AddressDao;
import org.path.iam.model.Address;
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
