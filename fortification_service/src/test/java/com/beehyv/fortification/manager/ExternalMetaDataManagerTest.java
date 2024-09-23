package com.beehyv.fortification.manager;

import com.beehyv.fortification.dao.ExternalMetaDataDao;
import com.beehyv.fortification.entity.ExternalMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExternalMetaDataManagerTest {

    @Mock
    private ExternalMetaDataDao externalMetaDataDao;

    private ExternalMetaDataManager externalMetaDataManager;

    private String key;
    private String service;
    private ExternalMetaData externalMetaData;

    @BeforeEach
    public void setUp() {
        externalMetaDataManager = new ExternalMetaDataManager(externalMetaDataDao);
        key = "key";
        service = "service";
        externalMetaData = new ExternalMetaData();

        when(externalMetaDataDao.findByKeyAndService(key, service)).thenReturn(externalMetaData);
    }

    @Test
    public void testFindByKeyAndService() {
        ExternalMetaData result = externalMetaDataManager.findByKeyAndService(key, service);
        assertEquals(externalMetaData, result);
    }
}