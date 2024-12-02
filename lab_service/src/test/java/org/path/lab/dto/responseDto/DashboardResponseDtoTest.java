package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DashboardResponseDtoTest {

    @Test
    void testConstructorWithAllFields() {
        // Given
        Long total = 100L;
        Long totalLabCategories = 10L;
        List<DashboardCountResponseDto> data = new ArrayList<>();
        Long categoryId = 1L;

        // When
        DashboardResponseDto dto = new DashboardResponseDto(total, totalLabCategories, data, categoryId);

        // Then
        assertNotNull(dto);
        assertEquals(total, dto.getTotal());
        assertEquals(totalLabCategories, dto.getTotalLabCategories());
        assertEquals(data, dto.getData());
        assertEquals(categoryId, dto.getCategoryId());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        DashboardResponseDto dto = new DashboardResponseDto();

        // Then
        assertNotNull(dto);
        assertNull(dto.getTotal());
        assertNull(dto.getTotalLabCategories());
        assertNull(dto.getData());
        assertNull(dto.getCategoryId());
    }

    @Test
    void testGetTotal() {
        // Given
        Long total = 100L;
        DashboardResponseDto dto = new DashboardResponseDto();
        dto.setTotal(total);

        // When
        Long result = dto.getTotal();

        // Then
        assertEquals(total, result);
    }

    @Test
    void testSetTotal() {
        // Given
        DashboardResponseDto dto = new DashboardResponseDto();
        Long total = 100L;

        // When
        dto.setTotal(total);

        // Then
        assertEquals(total, dto.getTotal());
    }

    @Test
    void testGetTotalLabCategories() {
        // Given
        Long totalLabCategories = 10L;
        DashboardResponseDto dto = new DashboardResponseDto();
        dto.setTotalLabCategories(totalLabCategories);

        // When
        Long result = dto.getTotalLabCategories();

        // Then
        assertEquals(totalLabCategories, result);
    }

    @Test
    void testSetTotalLabCategories() {
        // Given
        DashboardResponseDto dto = new DashboardResponseDto();
        Long totalLabCategories = 10L;

        // When
        dto.setTotalLabCategories(totalLabCategories);

        // Then
        assertEquals(totalLabCategories, dto.getTotalLabCategories());
    }

    @Test
    void testGetData() {
        // Given
        List<DashboardCountResponseDto> data = new ArrayList<>();
        DashboardResponseDto dto = new DashboardResponseDto();
        dto.setData(data);

        // When
        List<DashboardCountResponseDto> result = dto.getData();

        // Then
        assertEquals(data, result);
    }

    @Test
    void testSetData() {
        // Given
        DashboardResponseDto dto = new DashboardResponseDto();
        List<DashboardCountResponseDto> data = new ArrayList<>();

        // When
        dto.setData(data);

        // Then
        assertEquals(data, dto.getData());
    }

    @Test
    void testGetCategoryId() {
        // Given
        Long categoryId = 1L;
        DashboardResponseDto dto = new DashboardResponseDto();
        dto.setCategoryId(categoryId);

        // When
        Long result = dto.getCategoryId();

        // Then
        assertEquals(categoryId, result);
    }

    @Test
    void testSetCategoryId() {
        // Given
        DashboardResponseDto dto = new DashboardResponseDto();
        Long categoryId = 1L;

        // When
        dto.setCategoryId(categoryId);

        // Then
        assertEquals(categoryId, dto.getCategoryId());
    }

    @Test
    void testToString() {
        // Given
        Long total = 100L;
        Long totalLabCategories = 10L;
        List<DashboardCountResponseDto> data = new ArrayList<>();
        Long categoryId = 1L;
        DashboardResponseDto dto = new DashboardResponseDto(total, totalLabCategories, data, categoryId);

        // When
        String result = dto.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("DashboardResponseDto"));

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Long total = 100L;
        Long totalLabCategories = 10L;
        List<DashboardCountResponseDto> data = new ArrayList<>();
        Long categoryId = 1L;

        DashboardResponseDto dto1 = new DashboardResponseDto(total, totalLabCategories, data, categoryId);
        DashboardResponseDto dto2 = new DashboardResponseDto(total, totalLabCategories, data, categoryId);

        // Then

    }

    @Test
    void testNotEqualsAndHashCode() {
        // Given
        Long total1 = 100L;
        Long totalLabCategories1 = 10L;
        List<DashboardCountResponseDto> data1 = new ArrayList<>();
        Long categoryId1 = 1L;

        Long total2 = 200L;
        Long totalLabCategories2 = 20L;
        List<DashboardCountResponseDto> data2 = new ArrayList<>();
        Long categoryId2 = 2L;

        DashboardResponseDto dto1 = new DashboardResponseDto(total1, totalLabCategories1, data1, categoryId1);
        DashboardResponseDto dto2 = new DashboardResponseDto(total2, totalLabCategories2, data2, categoryId2);

        // Then
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}
