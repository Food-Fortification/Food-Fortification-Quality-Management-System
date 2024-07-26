package com.beehyv.iam.service;

import com.beehyv.iam.config.KeycloakCustomConfig;
import com.beehyv.iam.manager.ExternalMetaDataManager;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.manager.UserManager;
import com.beehyv.iam.manager.UserRoleCategoryManager;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    private static final RestTemplate restTemplate = new RestTemplate();
    @Mock
    private KeycloakCustomConfig keycloakCustomConfig;
    @Mock
    private ExternalMetaDataManager externalMetaDataManager;
    @Mock
    private UserManager userManager;
    @Mock
    private ManufacturerCategoryService manufacturerCategoryService;
    @Mock
    private UserRoleCategoryManager userRoleCategoryManager;
    @Mock
    private RoleService roleService;
    @Mock
    private KeycloakInfo keycloakInfo;
    @Mock
    private ManufacturerManager manufacturerManager;
    @InjectMocks
    private LoginService loginService;
    @Value("${config.realm.resource}")
    private String realmResource;
    @Value("${external.tcs.username}")
    private String tcsUsername;
    @Value("${external.tcs.password}")
    private String tcsPassword;
    @Value("${service.superadmin.username}")
    private String superadminUsername;
    @Value("${service.superadmin.password}")
    private String superadminPassword;
    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;
    @Value("${service.lab.baseUrl}")
    private String labBaseUrl;

    @BeforeEach
    public void setup() {
        // Initialize mocks and inject into the service
    }

//    @Test
//    public void testLogin() {
//        LoginRequestDto loginRequestDto = new LoginRequestDto();
//        loginRequestDto.setUserName("testUser");
//        loginRequestDto.setPassword("testPassword");
//
//        AccessTokenResponse mockResponse = new AccessTokenResponse();
//        when(keycloakCustomConfig.getAccessToken(any(LoginRequestDto.class))).thenReturn(mockResponse);
//
//        AccessTokenResponse response = loginService.login(loginRequestDto);
//
//        assertNotNull(response);
//        verify(keycloakCustomConfig).getAccessToken(loginRequestDto);
//    }


//    @Test
//    public void testExternalLogin_InvalidCredentials() {
//        LoginRequestDto loginRequestDto = new LoginRequestDto();
//        loginRequestDto.setUserName("invalidUser");
//        loginRequestDto.setPassword("invalidPassword");
//
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            loginService.externalLogin(loginRequestDto);
//        });
//
//        assertEquals("Invalid Credentials", exception.getMessage());
//    }


    @Test
    public void testRefreshToken() {
        String refreshToken = "dummyRefreshToken";
        AccessTokenResponse mockResponse = new AccessTokenResponse();
        when(keycloakCustomConfig.refreshToken(refreshToken)).thenReturn(mockResponse);

        AccessTokenResponse response = loginService.refreshToken(refreshToken);

        assertNotNull(response);
        verify(keycloakCustomConfig).refreshToken(refreshToken);
    }


//    @Test
//    public void testCheckLabUserLoginForMobile_Success() {
//        Set<String> roles = new HashSet<>();
//        roles.add("ROLE_MONITOR");
//        Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("roles", roles);
//
//        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
//
//        String result = loginService.checkLabUserLoginForMobile();
//
//        assertEquals("success", result);
//    }


}
