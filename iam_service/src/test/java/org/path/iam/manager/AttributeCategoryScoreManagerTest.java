package org.path.iam.manager;

import org.path.iam.dao.AttributeCategoryScoreDao;
import org.path.iam.model.AttributeCategoryScore;
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
class AttributeCategoryScoreManagerTest {

    @Mock
    private AttributeCategoryScoreDao attributeCategoryScoreDao;

    @InjectMocks
    private AttributeCategoryScoreManager attributeCategoryScoreManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindAll() {
        // Prepare test data
        List<AttributeCategoryScore> expectedScores = List.of(new AttributeCategoryScore(), new AttributeCategoryScore());
        when(attributeCategoryScoreDao.findAll(1, 10)).thenReturn(expectedScores);

        // Call the method to be tested
        List<AttributeCategoryScore> actualScores = attributeCategoryScoreManager.findAll(1, 10);

        // Verify the result
        assertEquals(expectedScores, actualScores);
    }

    @Test
    void testFindById() {
        // Prepare test data
        long scoreId = 1L;
        AttributeCategoryScore expectedScore = new AttributeCategoryScore();
        when(attributeCategoryScoreDao.findById(scoreId)).thenReturn(expectedScore);

        // Call the method to be tested
        AttributeCategoryScore actualScore = attributeCategoryScoreManager.findById(scoreId);

        // Verify the result
        assertEquals(expectedScore, actualScore);
    }

    @Test
    void testCreate() {
        // Prepare test data
        AttributeCategoryScore scoreToCreate = new AttributeCategoryScore();

        // Call the method to be tested
        attributeCategoryScoreManager.create(scoreToCreate);

        // Verify that the appropriate method in the DAO is called
        verify(attributeCategoryScoreDao, times(1)).create(scoreToCreate);
    }

    @Test
    void testUpdate() {
        // Prepare test data
        AttributeCategoryScore scoreToUpdate = new AttributeCategoryScore();

        // Call the method to be tested
        attributeCategoryScoreManager.update(scoreToUpdate);

        // Verify that the appropriate method in the DAO is called
        verify(attributeCategoryScoreDao, times(1)).update(scoreToUpdate);
    }

    @Test
    void testDelete() {
        // Prepare test data
        long scoreId = 1L;

        // Call the method to be tested
        attributeCategoryScoreManager.delete(scoreId);

        // Verify that the appropriate method in the DAO is called
        verify(attributeCategoryScoreDao, times(1)).delete(scoreId);
    }
}
