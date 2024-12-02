package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRoleCategoryRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long id = 1L;
        Long userId = 2L;
        String categoryName = "Sales";

        UserRoleCategoryRequestDto requestDto = new UserRoleCategoryRequestDto(id, userId, categoryName);

        assertNotNull(requestDto);
        assertEquals(id, requestDto.getId());
        assertEquals(userId, requestDto.getUserId());
        assertEquals(categoryName, requestDto.getCategoryName());
    }

    @Test
    public void testWithOptionalId() {
        Long userId = 3L;
        String categoryName = "Marketing";

        UserRoleCategoryRequestDto requestDto = new UserRoleCategoryRequestDto(null, userId, categoryName);

        assertNotNull(requestDto);
        assertNull(requestDto.getId());
        assertEquals(userId, requestDto.getUserId());
        assertEquals(categoryName, requestDto.getCategoryName());
    }


}
