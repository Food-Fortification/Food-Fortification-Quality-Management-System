package org.path.iam.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.path.iam.dto.requestDto.UserRoleCategoryRequestDto;
import org.path.iam.dto.responseDto.UserRoleCategoryResponseDto;
import org.path.iam.helper.EncryptionHelper;
import org.path.iam.model.Role;
import org.path.iam.model.User;
import org.path.iam.model.UserRoleCategory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserRoleCategoryMapperTest {

//    @MockBean
//    private EncryptionHelper encryptionHelper;
//
//    @BeforeEach
//    public void setup() {
//        // Ensure @PostConstruct is executed and static fields are initialized
//        ReflectionTestUtils.invokeMethod(encryptionHelper, "init");
//    }

    @Test
    void testToDto() {
        // Create a UserRoleCategory entity
        UserRoleCategory userRoleCategory = new UserRoleCategory();
        userRoleCategory.setId(1L);
        userRoleCategory.setUser(new User(1L));
        userRoleCategory.setCategory("Test Category");
        userRoleCategory.setRole(new Role(1L, "ADMIN", "Administrator"));

        // Create a UserRoleCategoryMapper instance
        UserRoleCategoryMapper userRoleCategoryMapper = new UserRoleCategoryMapper();

        // Perform mapping
        UserRoleCategoryResponseDto responseDto = userRoleCategoryMapper.toDto(userRoleCategory);

        // Assert
        assertEquals(1L, responseDto.getId());
        assertEquals(1L, responseDto.getUser().getId());
        assertEquals("Test Category", responseDto.getCategory());
        assertEquals(1L, responseDto.getRole().getId());
        assertEquals("ADMIN", responseDto.getRole().getName());
        assertEquals("Administrator", responseDto.getRole().getDisplayName());
    }

    @Test
    void testToEntity() {
        // Create a UserRoleCategoryRequestDto
        UserRoleCategoryRequestDto requestDto = new UserRoleCategoryRequestDto();
        requestDto.setId(1L);
        requestDto.setUserId(1L);
        requestDto.setCategoryName("Test Category");

        // Create a UserRoleCategoryMapper instance
        UserRoleCategoryMapper userRoleCategoryMapper = new UserRoleCategoryMapper();

        // Perform mapping
        UserRoleCategory entity = userRoleCategoryMapper.toEntity(requestDto);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals(1L, entity.getUser().getId());
        assertEquals("Test Category", entity.getCategory());
    }
}
