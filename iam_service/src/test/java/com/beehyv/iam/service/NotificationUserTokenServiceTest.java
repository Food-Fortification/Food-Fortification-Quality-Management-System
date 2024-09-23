package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.NotificationUserTokenRequestDto;
import com.beehyv.iam.manager.NotificationStateRoleMappingManager;
import com.beehyv.iam.manager.NotificationUserTokenManager;
import com.beehyv.iam.manager.UserManager;
import com.beehyv.iam.model.NotificationUserToken;
import com.beehyv.iam.model.User;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
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
