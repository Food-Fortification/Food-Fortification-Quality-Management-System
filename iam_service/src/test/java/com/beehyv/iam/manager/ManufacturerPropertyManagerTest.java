package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ManufacturerPropertyDao;
import com.beehyv.iam.model.ManufacturerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class ManufacturerPropertyManagerTest {

    @Mock
    private ManufacturerPropertyDao dao;

    @InjectMocks
    private ManufacturerPropertyManager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Prepare test data
        ManufacturerProperty manufacturerProperty = new ManufacturerProperty();

        // Call the method to be tested
        manager.create(manufacturerProperty);

        // Verify that the DAO method was called with the correct argument
        verify(dao).create(manufacturerProperty);
    }

    // Add more test cases for other methods here...
}
