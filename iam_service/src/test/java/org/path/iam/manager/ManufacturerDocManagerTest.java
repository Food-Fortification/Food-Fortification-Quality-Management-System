package org.path.iam.manager;

import org.path.iam.dao.ManufacturerDocDao;
import org.path.iam.model.ManufacturerDoc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerDocManagerTest {

    @Mock
    private ManufacturerDocDao dao;

    @InjectMocks
    private ManufacturerDocManager manager;


    @Test
    void testFindById() {
        // Prepare test data
        Long docId = 1L;
        ManufacturerDoc expectedDoc = new ManufacturerDoc(); // Create a sample ManufacturerDoc object

        // Mock the behavior of the DAO findById method
        when(dao.findById(docId)).thenReturn(expectedDoc);

        // Call the method to be tested
        ManufacturerDoc actualDoc = manager.findById(docId);

        // Verify that the correct ManufacturerDoc object was returned
        Assertions.assertEquals(expectedDoc, actualDoc);
    }

    // Add more test cases for other methods as needed
}
