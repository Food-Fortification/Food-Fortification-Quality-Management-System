package com.beehyv.iam.mapper;

import com.beehyv.iam.dto.requestDto.UserRoleCategoryRequestDto;
import com.beehyv.iam.dto.responseDto.UserRoleCategoryResponseDto;
import com.beehyv.iam.model.Role;
import com.beehyv.iam.model.User;
import com.beehyv.iam.model.UserRoleCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRoleCategoryMapperTest {

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
