package com.beehyv.iam.manager;

import com.beehyv.iam.dao.CountryDao;
import com.beehyv.iam.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryManagerTest {

    @Mock
    private CountryDao countryDao;

    @InjectMocks
    private CountryManager countryManager;

    @BeforeEach
    void setUp() {
        // You can initialize any mock behavior here
    }

    @Test
    void testFindAll() {
        // Prepare test data
        String search = "India";
        Integer pageNumber = 1;
        Integer pageSize = 10;
        List<Country> expectedCountries = List.of(new Country(), new Country());
        when(countryDao.findAll(search, pageNumber, pageSize)).thenReturn(expectedCountries);

        // Call the method to be tested
        List<Country> actualCountries = countryManager.findAll(search, pageNumber, pageSize);

        // Verify the result
        assertEquals(expectedCountries, actualCountries);
    }

    @Test
    void testGetCount() {
        // Prepare test data
        String search = "India";
        when(countryDao.getCount(search)).thenReturn(2L);

        // Call the method to be tested
        Long actualCount = countryManager.getCount(search);

        // Verify the result
        assertEquals(2L, actualCount);
    }

    @Test
    void testFindByName() {
        // Prepare test data
        String countryName = "India";
        Country expectedCountry = new Country();
        when(countryDao.findByName(countryName)).thenReturn(expectedCountry);

        // Call the method to be tested
        Country actualCountry = countryManager.findByName(countryName);

        // Verify the result
        assertEquals(expectedCountry, actualCountry);
    }
}
