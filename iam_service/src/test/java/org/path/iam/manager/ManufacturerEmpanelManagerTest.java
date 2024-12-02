package org.path.iam.manager;

import org.path.iam.dao.ManufacturerEmpanelDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ManufacturerEmpanelManagerTest {

    @Mock
    private ManufacturerEmpanelDao dao;

    @InjectMocks
    private ManufacturerEmpanelManager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmpanelledManufacturers() {
        // Prepare test data
        Long categoryId = 1L;
        String stateGeoId = "STATE1";
        Date fromDate = new Date();
        Date toDate = new Date();
        List<Long> expectedManufacturers = new ArrayList<>();
        expectedManufacturers.add(1L);
        expectedManufacturers.add(2L);

        // Mock the behavior of the DAO method
        when(dao.getAllEmpanelledManufacturers(categoryId, stateGeoId, fromDate, toDate))
                .thenReturn(expectedManufacturers);

        // Call the method to be tested
        List<Long> actualManufacturers = manager.getAllEmpanelledManufacturers(categoryId, stateGeoId, fromDate, toDate);

        // Verify that the DAO method was called with the correct arguments
        assertEquals(expectedManufacturers, actualManufacturers);
    }
}
