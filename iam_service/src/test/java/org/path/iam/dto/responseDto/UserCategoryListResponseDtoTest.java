package org.path.iam.dto.responseDto;

import org.path.iam.enums.RoleCategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserCategoryListResponseDtoTest {

    private UserCategoryListResponseDto userCategoryListResponseDto;

    @BeforeEach
    public void setUp() {
        userCategoryListResponseDto = new UserCategoryListResponseDto();
    }

    @Test
    public void testNoArgsConstructor() {
        UserCategoryListResponseDto dto = new UserCategoryListResponseDto();
        assertNull(dto.getCategoryNames());
        assertNull(dto.getNotificationLastSeenTime());
        assertNull(dto.getRoleCategoryType());
    }

    @Test
    public void testAllArgsConstructor() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Category1");
        LocalDateTime notificationLastSeenTime = LocalDateTime.now();
        RoleCategoryType roleCategoryType = RoleCategoryType.LAB;

        UserCategoryListResponseDto dto = new UserCategoryListResponseDto(categoryNames, notificationLastSeenTime, roleCategoryType);

        assertEquals(categoryNames, dto.getCategoryNames());
        assertEquals(notificationLastSeenTime, dto.getNotificationLastSeenTime());
        assertEquals(roleCategoryType, dto.getRoleCategoryType());
    }

    @Test
    public void testSettersAndGetters() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Category1");
        LocalDateTime notificationLastSeenTime = LocalDateTime.now();
        RoleCategoryType roleCategoryType = RoleCategoryType.LAB;

        userCategoryListResponseDto.setCategoryNames(categoryNames);
        userCategoryListResponseDto.setNotificationLastSeenTime(notificationLastSeenTime);
        userCategoryListResponseDto.setRoleCategoryType(roleCategoryType);

        assertEquals(categoryNames, userCategoryListResponseDto.getCategoryNames());
        assertEquals(notificationLastSeenTime, userCategoryListResponseDto.getNotificationLastSeenTime());
        assertEquals(roleCategoryType, userCategoryListResponseDto.getRoleCategoryType());
    }
}
