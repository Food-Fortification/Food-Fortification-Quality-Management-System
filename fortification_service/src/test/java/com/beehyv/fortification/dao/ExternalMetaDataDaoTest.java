package com.beehyv.fortification.dao;

import com.beehyv.fortification.entity.ExternalMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalMetaDataDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<ExternalMetaData> typedQuery;

    @InjectMocks
    private ExternalMetaDataDao externalMetaDataDao;

    @BeforeEach
    void setUp() {
        when(entityManager.createQuery(anyString(), eq(ExternalMetaData.class)))
                .thenReturn(typedQuery);
    }

    @Test
    void testFindByKeyAndService() {
        // Arrange
        String key = "key";
        String service = "service";
        ExternalMetaData expectedExternalMetaData = new ExternalMetaData();

        when(typedQuery.setParameter(anyString(), any()))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult())
                .thenReturn(expectedExternalMetaData);

        // Act
        ExternalMetaData actualExternalMetaData = externalMetaDataDao.findByKeyAndService(key, service);

        // Assert
        assertEquals(expectedExternalMetaData, actualExternalMetaData);
    }
}