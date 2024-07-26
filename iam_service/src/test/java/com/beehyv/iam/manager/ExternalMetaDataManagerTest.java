package com.beehyv.iam.manager;

import com.beehyv.iam.dao.ExternalMetaDataDao;
import com.beehyv.iam.model.ExternalMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalMetaDataManagerTest {

    @Mock
    private ExternalMetaDataDao externalMetaDataDao;

    @InjectMocks
    private ExternalMetaDataManager externalMetaDataManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindByKey() {
        // Prepare test data
        String key = "testKey";
        ExternalMetaData expectedMetaData = new ExternalMetaData();
        when(externalMetaDataDao.findByKey(key)).thenReturn(expectedMetaData);

        // Call the method to be tested
        ExternalMetaData actualMetaData = externalMetaDataManager.findByKey(key);

        // Verify the result
        assertEquals(expectedMetaData, actualMetaData);
    }

    // Add more test cases for other methods as needed
}
