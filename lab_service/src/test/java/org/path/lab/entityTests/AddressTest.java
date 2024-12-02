package org.path.lab.entityTests;


import org.path.lab.entity.Address;
import org.path.lab.entity.Village;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AddressTest {

    @Mock
    private Village village;

    @InjectMocks
    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String laneOneValue = "Lane One";
        String laneTwoValue = "Lane Two";
        String pinCodeValue = "123456";
        Double latitudeValue = 34.56;
        Double longitudeValue = 78.90;

        when(village.getId()).thenReturn(1L);

        address.setId(idValue);
        address.setLaneOne(laneOneValue);
        address.setLaneTwo(laneTwoValue);
        address.setVillage(village);
        address.setPinCode(pinCodeValue);
        address.setLatitude(latitudeValue);
        address.setLongitude(longitudeValue);

        assertEquals(idValue, address.getId());
        assertEquals(laneOneValue, address.getLaneOne());
        assertEquals(laneTwoValue, address.getLaneTwo());
        assertEquals(1L, address.getVillage().getId());
        assertEquals(pinCodeValue, address.getPinCode());
        assertEquals(latitudeValue, address.getLatitude());
        assertEquals(longitudeValue, address.getLongitude());
    }
}