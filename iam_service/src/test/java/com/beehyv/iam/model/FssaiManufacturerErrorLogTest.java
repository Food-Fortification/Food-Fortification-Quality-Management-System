package com.beehyv.iam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FssaiManufacturerErrorLogTest {

    private FssaiManufacturerErrorLog errorLog;

    @BeforeEach
    void setUp() {
        errorLog = new FssaiManufacturerErrorLog();
    }

    @Test
    void testFssaiManufacturerErrorLogInitialization() {
        assertNull(errorLog.getId());
        assertNull(errorLog.getData());
        assertNull(errorLog.getExceptionMessage());

    }

    @Test
    void testFssaiManufacturerErrorLogConstructorWithArgs() {
        Long id = 1L;
        String data = "Sample Data";
        String exceptionMessage = "Sample Exception Message";

        FssaiManufacturerErrorLog errorLog = new FssaiManufacturerErrorLog(id, data, exceptionMessage);

        assertEquals(id, errorLog.getId());
        assertEquals(data, errorLog.getData());
        assertEquals(exceptionMessage, errorLog.getExceptionMessage());

    }

    @Test
    void testIdSetterGetter() {

        Long id = 1L;

        errorLog.setId(id);

        assertEquals(id, errorLog.getId());
    }

    @Test
    void testDataSetterGetter() {
        String data = "Sample Data";

        errorLog.setData(data);

        assertEquals(data, errorLog.getData());
    }

    @Test
    void testExceptionMessageSetterGetter() {
        String exceptionMessage = "Sample Exception Message";

        errorLog.setExceptionMessage(exceptionMessage);

        assertEquals(exceptionMessage, errorLog.getExceptionMessage());
    }

    @Test
    void testIsDeletedSetterGetter() {
        errorLog.setIsDeleted(true);

        assertTrue(errorLog.getIsDeleted());
    }

    @Test
    void testToString() {
        // Arrange
        Long id = 1L;
        String data = "Sample Data";
        String exceptionMessage = "Sample Exception Message";
        errorLog = new FssaiManufacturerErrorLog(id, data, exceptionMessage);

        String toStringOutput = errorLog.toString();

        assertTrue(toStringOutput.contains("id=1"));
        assertTrue(toStringOutput.contains("data=Sample Data"));
        assertTrue(toStringOutput.contains("exceptionMessage=Sample Exception Message"));
    }

    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;
        String data = "Sample Data";
        String exceptionMessage = "Sample Exception Message";
        FssaiManufacturerErrorLog errorLog1 = new FssaiManufacturerErrorLog(id, data, exceptionMessage);
        FssaiManufacturerErrorLog errorLog2 = new FssaiManufacturerErrorLog(id, data, exceptionMessage);


    }
}
