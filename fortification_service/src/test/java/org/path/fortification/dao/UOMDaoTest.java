package org.path.fortification.dao;

import org.path.fortification.entity.UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UOMDaoTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<UOM> typedQuery;

    @InjectMocks
    private UOMDao uomDao;

    @BeforeEach
    void setUp() {
        when(em.createQuery(any(String.class), any(Class.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(any(String.class), any())).thenReturn(typedQuery);
    }

    @Test
    void testFindAllBIds() {
        // Arrange
        List<UOM> expectedUOMs = Collections.singletonList(new UOM());
        when(typedQuery.getResultList()).thenReturn(expectedUOMs);

        // Act
        List<UOM> actualUOMs = uomDao.findAllBIds(Collections.singletonList(1L));

        // Assert
        assertEquals(expectedUOMs, actualUOMs);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<UOM> expectedUOMs = Collections.singletonList(new UOM());
        when(typedQuery.getResultList()).thenReturn(expectedUOMs);

        // Act
        List<UOM> actualUOMs = uomDao.findAll(1, 10);

        // Assert
        assertEquals(expectedUOMs, actualUOMs);
    }

    @Test
    void testFindByConversionFactor() {
        // Arrange
        UOM expectedUOM = new UOM();
        when(typedQuery.getSingleResult()).thenReturn(expectedUOM);

        // Act
        UOM actualUOM = uomDao.findByConversionFactor(1L);

        // Assert
        assertEquals(expectedUOM, actualUOM);
    }

    @Test
    void testFindByName() {
        // Arrange
        UOM expectedUOM = new UOM();
        when(typedQuery.getSingleResult()).thenReturn(expectedUOM);

        // Act
        UOM actualUOM = uomDao.findByName("test");

        // Assert
        assertEquals(expectedUOM, actualUOM);
    }

}