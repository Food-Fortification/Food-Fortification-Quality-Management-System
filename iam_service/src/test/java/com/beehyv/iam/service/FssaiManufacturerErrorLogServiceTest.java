package com.beehyv.iam.service;

import com.beehyv.iam.manager.FssaiManufacturerErrorLogManager;
import com.beehyv.iam.model.FssaiManufacturerErrorLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class FssaiManufacturerErrorLogServiceTest {

    @Mock
    private FssaiManufacturerErrorLogManager fssaiManufacturerErrorLogManager;

    @InjectMocks
    private FssaiManufacturerErrorLogService fssaiManufacturerErrorLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        String data = "Test data";
        String exceptionMessage = "Test exception message";

        fssaiManufacturerErrorLogService.create(data, exceptionMessage);

        verify(fssaiManufacturerErrorLogManager, times(1)).create(any(FssaiManufacturerErrorLog.class));
    }
}
