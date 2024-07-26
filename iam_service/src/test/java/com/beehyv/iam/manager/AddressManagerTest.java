package com.beehyv.iam.manager;

import com.beehyv.iam.dao.AddressDao;
import com.beehyv.iam.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddressManagerTest {

    @Mock
    private AddressDao addressDao;

    @InjectMocks
    private AddressManager addressManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByManufacturerId() {
        // Mock data
        Long manufacturerId = 1L;
        Address expectedAddress = new Address();

        // Mock behavior
        when(addressDao.findByManufacturerId(manufacturerId)).thenReturn(expectedAddress);

        // Call the method
        Address actualAddress = addressManager.findByManufacturerId(manufacturerId);

        // Verify interactions and assertions
        verify(addressDao, times(1)).findByManufacturerId(manufacturerId);
        assertEquals(expectedAddress, actualAddress);
    }

    // Add more test cases for other methods if any
}
