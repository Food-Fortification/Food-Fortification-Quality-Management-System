package com.beehyv.lab.entityTests;


import com.beehyv.lab.entity.District;
import com.beehyv.lab.entity.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DistrictTest {

    @Mock
    private State state;

    @InjectMocks
    private District district;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllFields() {
        Long idValue = 1L;
        String nameValue = "Test District";
        String codeValue = "TD";
        String geoIdValue = "123456";

        when(state.getId()).thenReturn(1L);

        district.setId(idValue);
        district.setName(nameValue);
        district.setState(state);
        district.setCode(codeValue);
        district.setGeoId(geoIdValue);

        assertEquals(idValue, district.getId());
        assertEquals(nameValue, district.getName());
        assertEquals(1L, district.getState().getId());
        assertEquals(codeValue, district.getCode());
        assertEquals(geoIdValue, district.getGeoId());
    }
}