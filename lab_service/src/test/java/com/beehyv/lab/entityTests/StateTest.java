package com.beehyv.lab.entityTests;

import com.beehyv.lab.entity.State;
import com.beehyv.lab.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StateTest {

    @Mock
    private Country country;

    @InjectMocks
    private State state;

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

        when(country.getId()).thenReturn(1L);

        state.setId(idValue);
        state.setName(nameValue);
        state.setCountry(country);
        state.setCode(codeValue);
        state.setGeoId(geoIdValue);

        assertEquals(idValue, state.getId());
        assertEquals(nameValue, state.getName());
        assertEquals(1L, state.getCountry().getId());
        assertEquals(codeValue, state.getCode());
        assertEquals(geoIdValue, state.getGeoId());
    }
}