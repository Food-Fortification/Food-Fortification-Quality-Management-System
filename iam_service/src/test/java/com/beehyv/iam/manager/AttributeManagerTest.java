package com.beehyv.iam.manager;

import com.beehyv.iam.dao.AttributeDao;
import com.beehyv.iam.model.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttributeManagerTest {

    @Mock
    private AttributeDao attributeDao;

    @InjectMocks
    private AttributeManager attributeManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindByIds() {
        // Prepare test data
        List<Long> attributeIds = List.of(1L, 2L, 3L);
        List<Attribute> expectedAttributes = List.of(new Attribute(), new Attribute(), new Attribute());
        when(attributeDao.findByIds(attributeIds)).thenReturn(expectedAttributes);

        // Call the method to be tested
        List<Attribute> actualAttributes = attributeManager.findByIds(attributeIds);

        // Verify the result
        assertEquals(expectedAttributes, actualAttributes);
    }

    @Test
    void testCreate() {
        // Prepare test data
        Attribute attributeToCreate = new Attribute();

        // Call the method to be tested
        attributeManager.create(attributeToCreate);

        // Verify that the appropriate method in the DAO is called
        verify(attributeDao, times(1)).create(attributeToCreate);
    }

    @Test
    void testUpdate() {
        // Prepare test data
        Attribute attributeToUpdate = new Attribute();

        // Call the method to be tested
        attributeManager.update(attributeToUpdate);

        // Verify that the appropriate method in the DAO is called
        verify(attributeDao, times(1)).update(attributeToUpdate);
    }

    @Test
    void testDelete() {
        // Prepare test data
        long attributeId = 1L;

        // Call the method to be tested
        attributeManager.delete(attributeId);

        // Verify that the appropriate method in the DAO is called
        verify(attributeDao, times(1)).delete(attributeId);
    }
}
