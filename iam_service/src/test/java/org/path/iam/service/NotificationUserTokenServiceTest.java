package org.path.iam.service;

import org.path.iam.dto.requestDto.NotificationUserTokenRequestDto;
import org.path.iam.manager.NotificationStateRoleMappingManager;
import org.path.iam.manager.NotificationUserTokenManager;
import org.path.iam.manager.UserManager;
import org.path.iam.model.NotificationUserToken;
import org.path.iam.model.User;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class NotificationUserTokenServiceTest {

    @InjectMocks
    private NotificationUserTokenService notificationUserTokenService;

    @Mock
    private NotificationUserTokenManager notificationUserTokenManager;

    @Mock
    private NotificationStateRoleMappingManager notificationStateRoleMappingManager;

    @Mock
    private UserManager userManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ValidDto_ReturnsTokenId() {
        NotificationUserTokenRequestDto dto = new NotificationUserTokenRequestDto();
        dto.setUserName("testUser");
        dto.setRegistrationToken("testToken");

        User mockUser = new User();
        mockUser.setId(1L);
        when(userManager.findByName("testUser")).thenReturn(mockUser);

        when(keycloakInfo.getUserInfo()).thenReturn(Map.of("manufacturerId", 1L, "labId", 1L));

        NotificationUserToken mockToken = new NotificationUserToken();
        mockToken.setId(1L);
        when(notificationUserTokenManager.findByRegistrationToken("testToken", 1L)).thenReturn(null);
        when(notificationUserTokenManager.create(any(NotificationUserToken.class))).thenReturn(mockToken);

        Long tokenId = notificationUserTokenService.create(dto);

        assertNotNull(tokenId);
        assertEquals(1L, tokenId);
    }


}
