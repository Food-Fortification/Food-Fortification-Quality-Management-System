package org.path.lab.service.impl;
import org.path.lab.dto.responseDto.InspectionResponseDTO;
import org.path.lab.entity.Inspection;
import org.path.lab.manager.InspectionManager;
import org.path.lab.service.LabService;
import org.path.lab.helper.RestHelper;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InspectionServiceImplTest {

    @Mock
    private InspectionManager inspectionManager;

    @Mock
    private LabService labService;

    @Mock
    private RestHelper restHelper;

    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private RestHelper helper;

    @InjectMocks
    private InspectionServiceImpl inspectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetById() {
        when(inspectionManager.findById(1L)).thenReturn(new Inspection());

        InspectionResponseDTO result = inspectionService.getById(1L);

        assertNotNull(result);
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> inspectionService.delete(1L));
    }

}
