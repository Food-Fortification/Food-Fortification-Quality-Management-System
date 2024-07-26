package com.beehyv.iam.service;

import com.beehyv.iam.dto.requestDto.SearchListRequest;
import com.beehyv.iam.dto.requestDto.UserRequestDto;
import com.beehyv.iam.dto.responseDto.ListResponse;
import com.beehyv.iam.dto.responseDto.UserListResponseDto;
import com.beehyv.iam.enums.UserType;
import com.beehyv.iam.manager.ExternalMetaDataManager;
import com.beehyv.iam.manager.ManufacturerManager;
import com.beehyv.iam.manager.UserManager;
import com.beehyv.iam.manager.UserRoleCategoryManager;
import com.beehyv.iam.model.User;
import com.beehyv.parent.exceptions.CustomException;
import com.beehyv.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("existingEmail@example.com");

        when(userManager.findByEmail("existingEmail@example.com")).thenReturn(new User());

        assertThrows(CustomException.class, () -> userService.addUser(userRequestDto, false));
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
