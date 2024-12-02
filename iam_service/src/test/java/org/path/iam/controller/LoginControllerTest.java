package org.path.iam.controller;

import org.path.iam.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testLogin() {
//        LoginRequestDto dto = new LoginRequestDto();
//        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
//        when(loginService.login(any(LoginRequestDto.class))).thenReturn(accessTokenResponse);
//
//        ResponseEntity<?> response = loginController.login(dto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(accessTokenResponse, response.getBody());
//    }

    @Test
    void testLogout() {
        doNothing().when(loginService).logout();

        ResponseEntity<?> response = loginController.logout();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRefreshToken() {
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        when(loginService.refreshToken(anyString())).thenReturn(accessTokenResponse);

        ResponseEntity<?> response = loginController.refreshToken("some-refresh-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accessTokenResponse, response.getBody());
    }


//    @Test
//    void testExternalLogin() {
//        LoginRequestDto dto = new LoginRequestDto();
//        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
//        when(loginService.externalLogin(any(LoginRequestDto.class))).thenReturn(accessTokenResponse);
//
//        ResponseEntity<?> response = loginController.externalLogin(dto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(accessTokenResponse, response.getBody());
//    }
//
//    @Test
//    void testCheckLabUserLoginForMobile() {
//     String labUserLoginStatus =":";
//        when(loginService.checkLabUserLoginForMobile()).thenReturn((String) labUserLoginStatus);
//
//        ResponseEntity<?> response = loginController.checkLabUserLoginForMobile();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(labUserLoginStatus, response.getBody());
//    }
}
