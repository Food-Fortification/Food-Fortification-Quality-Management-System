package com.beehyv.iam.manager;

import com.beehyv.iam.dao.FssaiManufacturerErrorLogDao;
import com.beehyv.iam.model.FssaiManufacturerErrorLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FssaiManufacturerErrorLogManagerTest {

    @Mock
    private FssaiManufacturerErrorLogDao dao;

    @InjectMocks
    private FssaiManufacturerErrorLogManager manager;

    @Test
    void testCreate() {
        // Prepare test data
        FssaiManufacturerErrorLog errorLog = new FssaiManufacturerErrorLog();

        // Mock the behavior of the DAO
        doNothing().when(dao).create(errorLog);

        // Call the method to be tested
        manager.create(errorLog);

        // Verify that the create method of the DAO is called once with the correct argument
        verify(dao, times(1)).create(errorLog);
    }

    // Add more test cases for other methods as needed
}
