package org.path.fortification.manager;

import org.path.fortification.dao.ProductDao;
import org.path.fortification.entity.Product;
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
