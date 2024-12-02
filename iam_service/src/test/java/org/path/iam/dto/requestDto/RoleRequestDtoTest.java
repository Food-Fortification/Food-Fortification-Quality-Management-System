package org.path.iam.dto.requestDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleRequestDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String roleName = "Admin";
        String categoryName = "Management";
        Long categoryId = 1L;
        String roleCategoryType = "BUILT_IN";

        RoleRequestDto requestDto = new RoleRequestDto(roleName, categoryName, categoryId, roleCategoryType);

        assertNotNull(requestDto);
        assertEquals(roleName, requestDto.getRoleName());
        assertEquals(categoryName, requestDto.getCategoryName());
        assertEquals(categoryId, requestDto.getCategoryId());
        assertEquals(roleCategoryType, requestDto.getRoleCategoryType());
    }

    @Test
    public void testSetters() {
        RoleRequestDto requestDto = new RoleRequestDto();

        String newCategoryName = "Sales";
        String newRoleCategoryType = "CUSTOM";

        requestDto.setCategoryName(newCategoryName);
        requestDto.setRoleCategoryType(newRoleCategoryType);

        assertEquals(newCategoryName, requestDto.getCategoryName());
        assertEquals(newRoleCategoryType, requestDto.getRoleCategoryType());
    }


}
