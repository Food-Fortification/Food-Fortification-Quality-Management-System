package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.Village;
import com.beehyv.lab.entity.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class VillageTest {

    @Mock
    private District district;

    @InjectMocks
    private Village village;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Name";
        String codeValue = "Test Code";
        String geoIdValue = "Test GeoId";

        when(district.getId()).thenReturn(1L);

        village.setId(idValue);
        village.setName(nameValue);
        village.setDistrict(district);
        village.setCode(codeValue);
        village.setGeoId(geoIdValue);

        assertEquals(idValue, village.getId());
        assertEquals(nameValue, village.getName());
        assertEquals(1L, village.getDistrict().getId());
        assertEquals(codeValue, village.getCode());
        assertEquals(geoIdValue, village.getGeoId());
    }
}