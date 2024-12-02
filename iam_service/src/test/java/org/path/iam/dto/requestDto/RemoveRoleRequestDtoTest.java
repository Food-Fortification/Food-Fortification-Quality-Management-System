package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RemoveRoleRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String userName = "john.doe";
        Long roleCategoryId = 1L;

        RemoveRoleRequestDto requestDto = new RemoveRoleRequestDto(userName, roleCategoryId);

        assertNotNull(requestDto);
        assertEquals(userName, requestDto.getUserName());
        assertEquals(roleCategoryId, requestDto.getRoleCategoryId());
    }

    @Test
    public void testSetters() {
        RemoveRoleRequestDto requestDto = new RemoveRoleRequestDto("qq", 1L);

        String newUserName = "jane.smith";
        Long newRoleCategoryId = 2L;

        requestDto.setUserName(newUserName);
        requestDto.setRoleCategoryId(newRoleCategoryId);

        assertEquals(newUserName, requestDto.getUserName());
        assertEquals(newRoleCategoryId, requestDto.getRoleCategoryId());
    }

    // Add additional test cases for negative scenarios here (optional)
}
