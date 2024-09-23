package com.beehyv.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManufacturerEmpanelTest {

    private ManufacturerEmpanel manufacturerEmpanel;

    @BeforeEach
    void setUp() {
        manufacturerEmpanel = new ManufacturerEmpanel();
    }

    @Test
    void testManufacturerEmpanelInitialization() {
        assertNull(manufacturerEmpanel.getId());
        assertNull(manufacturerEmpanel.getManufacturerId());
        assertNull(manufacturerEmpanel.getCategoryId());
        assertNull(manufacturerEmpanel.getStateGeoId());
        assertNull(manufacturerEmpanel.getFromDate());
        assertNull(manufacturerEmpanel.getToDate());

    }

    @Test
    void testIdSetterGetter() {
        Long id = 1L;
        manufacturerEmpanel.setId(id);
        assertEquals(id, manufacturerEmpanel.getId());
    }

    @Test
    void testManufacturerIdSetterGetter() {
        Long manufacturerId = 1L;
        manufacturerEmpanel.setManufacturerId(manufacturerId);
        assertEquals(manufacturerId, manufacturerEmpanel.getManufacturerId());
    }

    @Test
    void testCategoryIdSetterGetter() {
        Long categoryId = 1L;
        manufacturerEmpanel.setCategoryId(categoryId);
        assertEquals(categoryId, manufacturerEmpanel.getCategoryId());
    }

    @Test
    void testStateGeoIdSetterGetter() {
        String stateGeoId = "IN-UP";
        manufacturerEmpanel.setStateGeoId(stateGeoId);
        assertEquals(stateGeoId, manufacturerEmpanel.getStateGeoId());
    }

    @Test
    void testFromDateSetterGetter() {
        Date fromDate = new Date();
        manufacturerEmpanel.setFromDate(fromDate);
        assertEquals(fromDate, manufacturerEmpanel.getFromDate());
    }

    @Test
    void testToDateSetterGetter() {
        Date toDate = new Date();
        manufacturerEmpanel.setToDate(toDate);
        assertEquals(toDate, manufacturerEmpanel.getToDate());
    }
}
