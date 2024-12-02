package org.path.lab.entityTests;


import org.path.lab.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryTest {

    @InjectMocks
    private Country country;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test Country";
        String codeValue = "TC";
        String geoIdValue = "123456";

        country.setId(idValue);
        country.setName(nameValue);
        country.setCode(codeValue);
        country.setGeoId(geoIdValue);

        assertEquals(idValue, country.getId());
        assertEquals(nameValue, country.getName());
        assertEquals(codeValue, country.getCode());
        assertEquals(geoIdValue, country.getGeoId());
    }
}