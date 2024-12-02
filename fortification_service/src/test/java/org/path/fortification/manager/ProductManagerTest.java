package org.path.fortification.manager;

import org.path.fortification.dao.ProductDao;
import org.path.fortification.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductManagerTest {

    @Mock
    private ProductDao productDao;

    private ProductManager productManager;

    private String name;
    private Product product;

    @BeforeEach
    public void setUp() {
        productManager = new ProductManager(productDao);
        name = "product1";
        product = new Product();

        when(productDao.findByName(name)).thenReturn(Optional.of(product));
    }

    @Test
    public void testFindByName() {
        Optional<Product> result = productManager.findByName(name);
        assertEquals(Optional.of(product), result);
    }
}