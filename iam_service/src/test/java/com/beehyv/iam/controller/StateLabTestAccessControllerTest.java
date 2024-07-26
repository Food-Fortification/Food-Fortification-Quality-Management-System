package com.beehyv.iam.controller;

import com.beehyv.iam.dto.requestDto.StateLabTestAccessRequestDto;
import com.beehyv.iam.dto.responseDto.StateLabTestAccessResponseDto;
import com.beehyv.iam.enums.EntityType;
import com.beehyv.iam.enums.StateLabTestAccessType;
import com.beehyv.iam.service.StateLabTestAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class StateLabTestAccessControllerTest {

    @Mock
    private StateLabTestAccessService stateLabTestAccessService;

    @InjectMocks
    private StateLabTestAccessController stateLabTestAccessController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByStateAndCategoryAndEntityType() {
        StateLabTestAccessResponseDto responseDtoList = new StateLabTestAccessResponseDto();
        when(stateLabTestAccessService.getByStateAndCategoryAndEntityType(anyLong(), any(EntityType.class), anyLong()))
                .thenReturn(responseDtoList);

        ResponseEntity<?> response = stateLabTestAccessController.getByStateAndCategoryAndEntityType(1L, EntityType.lot, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoList, response.getBody());
    }

    @Test
    void testGetByManufacturerAndCategoryAndEntityType() {
        List<StateLabTestAccessResponseDto> responseDtoList = new ArrayList<>();
        when(stateLabTestAccessService.findByStateAndCategoryAndEntityType(anyLong(), anyLong(), any(EntityType.class), any(StateLabTestAccessType.class)))
                .thenReturn(true);

        ResponseEntity<?> response = stateLabTestAccessController.getByManufacturerAndCategoryAndEntityType(1L, 2L, EntityType.lot, StateLabTestAccessType.LAB_SELECTION);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetIsManufacturerRawMaterialsProcured() {
        boolean isProcured = true;
        when(stateLabTestAccessService.getIsManufacturerWarehouseStateProcured(anyLong(), anyLong(), any(EntityType.class)))
                .thenReturn(isProcured);

        ResponseEntity<?> response = stateLabTestAccessController.getIsManufacturerRawMaterialsProcured(1L, 2L, EntityType.lot);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(isProcured, response.getBody());
    }

    @Test
    void testCreate() {
        doNothing().when(stateLabTestAccessService).create(any(StateLabTestAccessRequestDto.class));

        ResponseEntity<?> response = stateLabTestAccessController.create(new StateLabTestAccessRequestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Successfully Created entity", response.getBody());
    }


}
