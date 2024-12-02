package org.path.iam.controller;

import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.responseDto.UserResponseDto;
import org.path.iam.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        when(service.addUser(any(UserRequestDto.class), anyBoolean())).thenReturn(1L);

        ResponseEntity<?> response = controller.addUser(new UserRequestDto());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetUser() {
        UserResponseDto userResponseDto = new UserResponseDto();
        when(service.getUser(1L)).thenReturn(userResponseDto);

        ResponseEntity<?> response = controller.getUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
