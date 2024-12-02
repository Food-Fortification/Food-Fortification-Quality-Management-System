package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.StatusRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.StatusResponseDto;
import org.path.fortification.entity.Status;
import org.path.fortification.manager.StatusManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusServiceImplTest {

    @Mock
    private StatusManager manager;

    @InjectMocks
    private StatusServiceImpl statusService;

    private StatusRequestDto requestDto;
    private Status status;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new StatusRequestDto();
        requestDto.setName("Active");
        requestDto.setDescription("This status indicates an active state.");

        status = new Status();
        status.setId(1L);
        status.setName("Active");
        status.setDescription("This status indicates an active state.");
    }

    @Test
    void createStatus_ValidRequest_ShouldCreateStatus() {
        when(manager.create(any(Status.class))).thenReturn(status);

        statusService.createStatus(requestDto);

        verify(manager, times(1)).create(any(Status.class));
    }

    @Test
    void getStatusById_ValidId_ShouldReturnStatusDto() {
        when(manager.findById(status.getId())).thenReturn(status);

        StatusResponseDto response = statusService.getStatusById(status.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(status.getId(), response.getId());
        Assertions.assertEquals(status.getName(), response.getName());
        Assertions.assertEquals(status.getDescription(), response.getDescription());
    }

    @Test
    void getAllStatuses_ValidRequest_ShouldReturnStatusList() {
        List<Status> statuses = new ArrayList<>();
        Status status1 = new Status();
        status1.setId(1L);
        Status status2 = new Status();
        status2.setId(2L);
        statuses.add(status1);
        statuses.add(status2);

        when(manager.findAll(0, 10)).thenReturn(statuses);
        when(manager.getCount(statuses.size(), 0, 10)).thenReturn(2L);

        ListResponse<StatusResponseDto> response = statusService.getAllStatuses(0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getCount());
    }

    @Test
    void updateStatus_ValidRequest_ShouldUpdateStatus() {
        requestDto.setId(status.getId());
        requestDto.setName("Updated Name");
        requestDto.setDescription("Updated description");

        when(manager.findById(status.getId())).thenReturn(status);

        statusService.updateStatus(requestDto);

        Assertions.assertEquals("Updated Name", status.getName());
        Assertions.assertEquals("Updated description", status.getDescription());
        verify(manager, times(1)).update(status);
    }

    @Test
    void deleteStatus_ValidId_ShouldDeleteStatus() {
        statusService.deleteStatus(status.getId());

        verify(manager, times(1)).delete(status.getId());
    }
}


//tests include-
//createStatus_ValidRequest_ShouldCreateStatus: This test verifies that the createStatus method creates a new Status entity correctly.
//getStatusById_ValidId_ShouldReturnStatusDto: This test verifies that the getStatusById method returns the correct StatusResponseDto for a given Status ID.
//getAllStatuses_ValidRequest_ShouldReturnStatusList: This test verifies that the getAllStatuses method returns a ListResponse containing the correct list of StatusResponseDto objects.
//updateStatus_ValidRequest_ShouldUpdateStatus: This test verifies that the updateStatus method updates an existing Status entity correctly with the provided StatusRequestDto.
//deleteStatus_ValidId_ShouldDeleteStatus: This test verifies that the deleteStatus method deletes an existing Status entity for the given ID.