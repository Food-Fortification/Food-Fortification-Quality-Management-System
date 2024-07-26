package com.beehyv.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DistrictTest {

    private District district;

    @BeforeEach
    void setUp() {
        district = new District();
    }


    @Test
    void testDistrictConstructorWithArgs() {
        Long id = 1L;
        String name = "DistrictName";
        String geoId = "GeoId123";
        State state = new State();
        String code = "DistrictCode";
        Set<Village> villages = new HashSet<>();

        District district = new District(id, name, geoId, state, code, villages);

        assertEquals(id, district.getId());
        assertEquals(name, district.getName());
        assertEquals(geoId, district.getGeoId());
        assertEquals(state, district.getState());
        assertEquals(code, district.getCode());
        assertEquals(villages, district.getVillages());
    }

    @Test
    void testIdSetterGetter() {
        Long id = 1L;

        district.setId(id);

        assertEquals(id, district.getId());
    }

    @Test
    void testNameSetterGetter() {
        String name = "DistrictName";

        district.setName(name);

        assertEquals(name, district.getName());
    }

    @Test
    void testGeoIdSetterGetter() {
        String geoId = "GeoId123";

        district.setGeoId(geoId);

        assertEquals(geoId, district.getGeoId());
    }

    @Test
    void testStateSetterGetter() {
        State state = new State();

        district.setState(state);

        assertEquals(state, district.getState());
    }

    @Test
    void testCodeSetterGetter() {
        String code = "DistrictCode";

        district.setCode(code);

        assertEquals(code, district.getCode());
    }

    @Test
    void testVillagesSetterGetter() {
        Set<Village> villages = new HashSet<>();
        Village village1 = new Village();
        Village village2 = new Village();
        villages.add(village1);
        villages.add(village2);

        district.setVillages(villages);

        assertEquals(villages, district.getVillages());
    }

    @Test
    void testAddVillage() {
        Village village = new Village();

        district.getVillages().add(village);

        assertTrue(district.getVillages().contains(village));
    }

    @Test
    void testRemoveVillage() {
        Village village = new Village();
        district.getVillages().add(village);

        district.getVillages().remove(village);

        assertFalse(district.getVillages().contains(village));
    }

    @Test
    void testIsDeletedSetterGetter() {
        district.setIsDeleted(true);

        assertTrue(district.getIsDeleted());
    }


}
