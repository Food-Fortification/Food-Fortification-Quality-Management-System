package com.beehyv.lab.service.impl;
import com.beehyv.lab.dto.external.ExternalInspectionRequestDto;
import com.beehyv.lab.dto.requestDto.InspectionRequestDTO;
import com.beehyv.lab.dto.requestDto.LabSampleRequestDTO;
import com.beehyv.lab.dto.responseDto.InspectionResponseDTO;
import com.beehyv.lab.entity.Inspection;
import com.beehyv.lab.entity.Lab;
import com.beehyv.lab.enums.SampleType;
import com.beehyv.lab.manager.InspectionManager;
import com.beehyv.lab.service.LabService;
import com.beehyv.lab.helper.Constants;
import com.beehyv.lab.helper.RestHelper;
import com.beehyv.lab.service.impl.InspectionServiceImpl;
import com.beehyv.lab.dto.responseDto.LabResponseDTO;
import com.beehyv.lab.dto.external.ExternalLotDetailsResponseDto;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
