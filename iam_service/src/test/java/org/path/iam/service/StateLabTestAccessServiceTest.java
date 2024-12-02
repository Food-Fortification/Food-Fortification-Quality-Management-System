package org.path.iam.service;

import org.path.iam.dto.requestDto.StateLabTestAccessRequestDto;
import org.path.iam.dto.responseDto.StateLabTestAccessResponseDto;
import org.path.iam.enums.EntityType;
import org.path.iam.enums.StateLabTestAccessType;
import org.path.iam.manager.ManufacturerManager;
import org.path.iam.manager.StateLabTestAccessManager;
import org.path.iam.manager.StatusManager;
import org.path.iam.model.*;
import org.path.iam.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StateLabTestAccessServiceTest {

    @Mock
    private StateLabTestAccessManager stateLabTestAccessManager;

    @Mock
    private ManufacturerManager manufacturerManager;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private StatusManager statusManager;

    @InjectMocks
    private StateLabTestAccessService stateLabTestAccessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStateLabTestAccess() {
        StateLabTestAccessRequestDto requestDto = new StateLabTestAccessRequestDto();
        StateLabTestAccess stateLabTestAccess = new StateLabTestAccess();
        Status status = new Status();
        status.setId(1L);
        stateLabTestAccess.setStatus(status);
        when(dtoMapper.mapToEntity(requestDto)).thenReturn(stateLabTestAccess);

        stateLabTestAccessService.create(requestDto);

        verify(dtoMapper).mapToEntity(requestDto);
        verify(stateLabTestAccessManager).create(stateLabTestAccess);
    }

    @Test
    void testUpdateStateLabTestAccess() {
        // Arrange
        StateLabTestAccessRequestDto requestDto = new StateLabTestAccessRequestDto();
        Long categoryId = 1L;
        EntityType entityType = EntityType.lot;
        Long stateId = 100L;
        StateLabTestAccess existingStateLabTestAccess = new StateLabTestAccess();
        when(stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId, entityType, stateId)).thenReturn(existingStateLabTestAccess);

        // Act
        stateLabTestAccessService.update(requestDto, categoryId, entityType, stateId);

        // Assert
        verify(stateLabTestAccessManager).findByStateAndCategoryAndEntityType(categoryId, entityType, stateId);
        verify(stateLabTestAccessManager).update(existingStateLabTestAccess);
    }

    @Test
    void testGetByStateAndCategoryAndEntityType() {
        // Arrange
        Long categoryId = 1L;
        EntityType entityType = EntityType.lot;
        Long stateId = 100L;
        StateLabTestAccess stateLabTestAccess = new StateLabTestAccess();
        when(stateLabTestAccessManager.findByStateAndCategoryAndEntityType(categoryId, entityType, stateId)).thenReturn(stateLabTestAccess);

        StateLabTestAccessResponseDto responseDto = stateLabTestAccessService.getByStateAndCategoryAndEntityType(categoryId, entityType, stateId);


    }

    @Test
    void testFindByStateAndCategoryAndEntityType() {
        Long manufacturerId = 1L;
        Long categoryId = 1L;
        EntityType entityType = EntityType.lot;
        StateLabTestAccessType stateLabTestAccessType = StateLabTestAccessType.LAB_SELECTION;
        Manufacturer manufacturer = new Manufacturer();
        Address address = new Address();
        Village village = new Village();
        District district = new District();
        State state = new State();
        state.setId(1L);
        district.setState(state);
        village.setDistrict(district);
        address.setVillage(village);
        manufacturer.setAddress(address);
        when(manufacturerManager.findById(manufacturerId)).thenReturn(manufacturer);
//           when(manufacturer.getAddress().getVillage().getDistrict().getState().getId()).thenReturn(1L);
        Boolean result = stateLabTestAccessService.findByStateAndCategoryAndEntityType(manufacturerId, categoryId, entityType, stateLabTestAccessType);

        assertTrue(result);
    }
}

