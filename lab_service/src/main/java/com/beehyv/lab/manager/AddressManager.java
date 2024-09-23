package com.beehyv.lab.manager;

import com.beehyv.lab.dao.AddressDao;
import com.beehyv.lab.dao.DistrictDao;
import com.beehyv.lab.entity.Address;
import com.beehyv.lab.entity.District;
import org.springframework.stereotype.Component;

@Component
public class AddressManager extends BaseManager<Address, AddressDao>{
    public AddressManager(AddressDao dao) {
        super(dao);
    }
}
