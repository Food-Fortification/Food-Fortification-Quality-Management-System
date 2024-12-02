package org.path.fortification.dao;

import org.path.fortification.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Product> typedQuery;

    @InjectMocks
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), any(Class.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
    }

    @Test
    void testFindByName() {
        // Arrange
        Product expectedProduct = new Product();
        when(typedQuery.getSingleResult()).thenReturn(expectedProduct);

        // Act
        Optional<Product> actualProduct = productDao.findByName("test");

        // Assert
        assertEquals(Optional.of(expectedProduct), actualProduct);
    }

}
