package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        Long total = 100L;
        Long totalManufacturerCategories = 20L;
        List<DashboardCountResponseDto> data = new ArrayList<>();
        data.add(new DashboardCountResponseDto("ADMIN_ATTR_COUNT", 50L, 10L));
        Long categoryId = 1L;

        DashboardResponseDto responseDto = new DashboardResponseDto(total, totalManufacturerCategories, data, categoryId);

        assertNotNull(responseDto);
        assertEquals(total, responseDto.getTotal());
        assertEquals(totalManufacturerCategories, responseDto.getTotalManufacturerCategories());
        assertEquals(data, responseDto.getData());
        assertEquals(categoryId, responseDto.getCategoryId());
    }

    @Test
    public void testWithEmptyList() {
        Long total = 0L;
        Long totalManufacturerCategories = null;
        List<DashboardCountResponseDto> data = new ArrayList<>();
        Long categoryId = 2L;

        DashboardResponseDto responseDto = new DashboardResponseDto(total, totalManufacturerCategories, data, categoryId);

        assertNotNull(responseDto);
        assertEquals(total, responseDto.getTotal());
        assertNull(totalManufacturerCategories);
        assertTrue(responseDto.getData().isEmpty());
        assertEquals(categoryId, responseDto.getCategoryId());
    }
}
