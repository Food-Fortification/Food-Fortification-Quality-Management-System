package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.ProductDao;
import com.beehyv.fortification.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductManager extends BaseManager<Product, ProductDao> {
    private final ProductDao dao;
    public ProductManager(ProductDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Optional<Product> findByName(String name) {
        return dao.findByName(name);
    }
}
