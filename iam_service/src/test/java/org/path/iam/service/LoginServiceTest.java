package org.path.iam.service;

import org.path.iam.config.KeycloakCustomConfig;
import org.path.iam.manager.ExternalMetaDataManager;
import org.path.iam.manager.ManufacturerManager;
import org.path.iam.manager.UserManager;
import org.path.iam.manager.UserRoleCategoryManager;
import org.path.parent.keycloakSecurity.KeycloakInfo;
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
    @Value("${service.fortification.baseUrl}")
    private String fortificationBaseUrl;
    @Value("${service.lab.baseUrl}")
    private String labBaseUrl;

    @BeforeEach
    public void setup() {
        // Initialize mocks and inject into the service
    }

    @Test
    public void testRefreshToken() {
        String refreshToken = "dummyRefreshToken";
        AccessTokenResponse mockResponse = new AccessTokenResponse();
        when(keycloakCustomConfig.refreshToken(refreshToken)).thenReturn(mockResponse);

        AccessTokenResponse response = loginService.refreshToken(refreshToken);

        assertNotNull(response);
        verify(keycloakCustomConfig).refreshToken(refreshToken);
    }



}
