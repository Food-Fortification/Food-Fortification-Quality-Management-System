package org.path.lab.manager;

import org.path.lab.dao.AddressDao;
import org.path.lab.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressManager extends BaseManager<Address, AddressDao>{
    public AddressManager(AddressDao dao) {
        super(dao);
    }
}
