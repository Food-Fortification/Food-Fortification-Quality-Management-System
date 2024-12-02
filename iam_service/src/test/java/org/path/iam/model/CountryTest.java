package org.path.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
    }

    @Test
    void testCountryConstructorWithArgs() {
        Long id = 1L;
        String name = "CountryName";
        String code = "CountryCode";
        String geoId = "GeoId123";
        Set<State> states = new HashSet<>();

        Country country = new Country(id, name, code, geoId, states);

        assertEquals(id, country.getId());
        assertEquals(name, country.getName());
        assertEquals(code, country.getCode());
        assertEquals(geoId, country.getGeoId());
        assertEquals(states, country.getStates());
    }

    @Test
    void testIdSetterGetter() {
        Long id = 1L;

        country.setId(id);

        assertEquals(id, country.getId());
    }

    @Test
    void testNameSetterGetter() {
        String name = "CountryName";

        country.setName(name);

        assertEquals(name, country.getName());
    }

    @Test
    void testCodeSetterGetter() {
        String code = "CountryCode";

        country.setCode(code);

        assertEquals(code, country.getCode());
    }

    @Test
    void testGeoIdSetterGetter() {
        String geoId = "GeoId123";

        country.setGeoId(geoId);

        assertEquals(geoId, country.getGeoId());
    }

    @Test
    void testStatesSetterGetter() {
        Set<State> states = new HashSet<>();
        State state1 = new State();
        State state2 = new State();
        states.add(state1);
        states.add(state2);

        country.setStates(states);

        assertEquals(states, country.getStates());
    }

    @Test
    void testAddState() {
        State state = new State();

        country.getStates().add(state);

        assertTrue(country.getStates().contains(state));
    }

    @Test
    void testRemoveState() {
        // Arrange
        State state = new State();
        country.getStates().add(state);

        // Act
        country.getStates().remove(state);

        // Assert
        assertFalse(country.getStates().contains(state));
    }

    @Test
    void testIsDeletedSetterGetter() {
        // Act
        country.setIsDeleted(true);

        // Assert
        assertTrue(country.getIsDeleted());
    }


}
