package org.path.iam.service;

import org.mockito.MockedStatic;
import org.path.iam.dto.requestDto.SearchListRequest;
import org.path.iam.dto.requestDto.UserRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.UserListResponseDto;
import org.path.iam.enums.UserType;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.manager.ExternalMetaDataManager;
import org.path.iam.manager.ManufacturerManager;
import org.path.iam.manager.UserManager;
import org.path.iam.manager.UserRoleCategoryManager;
import org.path.iam.model.User;
import org.path.parent.exceptions.CustomException;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    ExternalMetaDataManager externalMetaDataManager;
    @Mock
    LoginService loginService;
    @Mock
    UserRoleCategoryManager userRoleCategoryManager;
    @Mock
    private UserManager userManager;
    @Mock
    private KeycloakInfo keycloakInfo;

    @Mock
    private ManufacturerManager manufacturerManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;
   /* @Test
    void testAddUser_SuccessfulRegistration() {
        // Prepare test data
        UserRequestDto userRequestDto = new UserRequestDto();
        // Set user details

        // Mock behavior
        when(manufacturerManager.findById(anyLong())).thenReturn(new Manufacturer());
        when(userManager.create(any(User.class))).thenReturn(new User());
        doNothing().when(userService).checkPassword(any());
        // Call the method
        userService.addUser(userRequestDto, false);

        // Verify that the user was successfully added
        // Add appropriate verification statements
    }*/


    @Test
    void testAddUser_ThrowsException_WhenUserNameExists() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserName("existingUserName");

        when(userManager.findByName("existingUserName")).thenReturn(new User());

        assertThrows(CustomException.class, () -> userService.addUser(userRequestDto, false));
    }

    @Test
    void testAddUser_ThrowsException_WhenUserEmailExists() {
        try (MockedStatic<EncryptionHelper> mockedStatic = mockStatic(EncryptionHelper.class)) {
            // Prepare test data
            String email = "existingEmail@example.com";
            String encryptedEmail = "mockedEncryptedString";

            // Mock EncryptionHelper to return the encrypted string
            mockedStatic.when(() -> EncryptionHelper.encrypt(email)).thenReturn(encryptedEmail);

            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setEmail(email);

            // Mock the behavior of userManager.findByEmail to return a user with the encrypted email
            when(userManager.findByEmail(encryptedEmail)).thenReturn(new User());

            // Call the method and assert that CustomException is thrown
            assertThrows(CustomException.class, () -> userService.addUser(userRequestDto, false));
        }
    }





    @Test
    void testAddUser_ThrowsException_WhenPasswordDoesNotMeetRequirements() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserName("testUser");
        userRequestDto.setFirstName("John");
        userRequestDto.setLastName("Doe");
        userRequestDto.setEmail("test@example.com");
        userRequestDto.setPassword("weakpassword"); // Password doesn't meet requirements

        when(userManager.findByName(any())).thenReturn(null);
        when(userManager.findByEmail(any())).thenReturn(null);

        assertThrows(CustomException.class, () -> userService.addUser(userRequestDto, false));
    }

    @Test
    void testCheckPassword_ValidPassword() {
        String validPassword = "Abc@1234";

        assertDoesNotThrow(() -> userService.checkPassword(validPassword));

    }
/*
    @Test
    void testDeleteUser_SuccessfulDeletion() {
        String userName = "testUser";

        when(keycloakInfo.getUserInfo()).thenReturn(Collections.singletonMap("roles", Collections.singleton("SUPERADMIN")));
        when(userManager.findByName(userName)).thenReturn(new User());
        when(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0L)).thenReturn(1L);
        UsersResource usersResourceMock = mock(UsersResource.class);
        when(userService.getInstance()).thenReturn(usersResourceMock);
        when(usersResourceMock.search(anyString(), anyBoolean())).thenReturn(Collections.singletonList(new UserRepresentation()));
        when(usersResourceMock.get(anyString())).thenReturn(mock(UserResource.class));
        doNothing().when(userManager).delete(anyLong());

        userService.deleteUser(userName);


    }*/


    @Test
    void testGetAllUsers_SuccessfulRetrieval() {
        // Prepare test data
        SearchListRequest searchListRequest = new SearchListRequest();
        Integer pageNumber = 1;
        Integer pageSize = 10;
        UserType type = UserType.MANUFACTURER;

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", Collections.singleton("ADMIN"));
        userInfo.put("manufacturerId", 123L);

        // Mock behavior
        when(keycloakInfo.getUserInfo()).thenReturn(userInfo);
        when(userRoleCategoryManager.findAllUsersBySearchAndFilter(any(), anyInt(), anyInt(), eq(123L))).thenReturn(Collections.emptyList());
        when(userRoleCategoryManager.getCountForALlUsersBySearchAndFilter(any(), eq(123L))).thenReturn(0L);

        ListResponse<UserListResponseDto> response = userService.getAllUsers(searchListRequest, pageNumber, pageSize, type);

        assertNotNull(response);
        assertTrue(response.getData().isEmpty());
    }


}
