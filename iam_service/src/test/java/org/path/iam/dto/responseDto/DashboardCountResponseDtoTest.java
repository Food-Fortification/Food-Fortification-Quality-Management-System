package org.path.iam.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardCountResponseDtoTest {

    @Test
    public void testValidConstructionAndGetters() {
        String id = "ADMIN_CATEGORY_COUNT";
        Long total = 10L;
        Long totalManufacturerCategories = 5L;

        DashboardCountResponseDto responseDto = new DashboardCountResponseDto(id, total, totalManufacturerCategories);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals(total, responseDto.getTotal());
        assertEquals(totalManufacturerCategories, responseDto.getTotalManufacturerCategories());
    }

    @Test
    public void testWithOptionalFields() {
        String id = "MANUFACTURER_SCORE_COUNT";
        Long total = null;
        DashboardCountResponseDto responseDto = new DashboardCountResponseDto(id, total, null);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertNull(responseDto.getTotal());
        assertNull(responseDto.getTotalManufacturerCategories());
    }
}
