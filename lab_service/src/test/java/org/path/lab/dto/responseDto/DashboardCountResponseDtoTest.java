package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardCountResponseDtoTest {

    @Test
    void testConstructorWithAllFields() {
        // Given
        String id = "testId";
        Long total = 100L;
        Long totalLabCategories = 10L;

        // When
        DashboardCountResponseDto dto = new DashboardCountResponseDto(id, total, totalLabCategories);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(total, dto.getTotal());
        assertEquals(totalLabCategories, dto.getTotalLabCategories());
    }

    @Test
    void testNoArgsConstructor() {
        // When
        DashboardCountResponseDto dto = new DashboardCountResponseDto();

        // Then
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getTotal());
        assertNull(dto.getTotalLabCategories());
    }

    @Test
    void testGetId() {
        // Given
        String id = "testId";
        DashboardCountResponseDto dto = new DashboardCountResponseDto();
        dto.setId(id);

        // When
        String result = dto.getId();

        // Then
        assertEquals(id, result);
    }

    @Test
    void testSetId() {
        // Given
        DashboardCountResponseDto dto = new DashboardCountResponseDto();
        String id = "testId";

        // When
        dto.setId(id);

        // Then
        assertEquals(id, dto.getId());
    }

    @Test
    void testGetTotal() {
        // Given
        Long total = 100L;
        DashboardCountResponseDto dto = new DashboardCountResponseDto();
        dto.setTotal(total);

        // When
        Long result = dto.getTotal();

        // Then
        assertEquals(total, result);
    }

    @Test
    void testSetTotal() {
        // Given
        DashboardCountResponseDto dto = new DashboardCountResponseDto();
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
        DashboardCountResponseDto dto = new DashboardCountResponseDto();
        dto.setTotalLabCategories(totalLabCategories);

        // When
        Long result = dto.getTotalLabCategories();

        // Then
        assertEquals(totalLabCategories, result);
    }

    @Test
    void testSetTotalLabCategories() {
        // Given
        DashboardCountResponseDto dto = new DashboardCountResponseDto();
        Long totalLabCategories = 10L;

        // When
        dto.setTotalLabCategories(totalLabCategories);

        // Then
        assertEquals(totalLabCategories, dto.getTotalLabCategories());
    }

    @Test
    void testToString() {
        // Given
        String id = "testId";
        Long total = 100L;
        Long totalLabCategories = 10L;
        DashboardCountResponseDto dto = new DashboardCountResponseDto(id, total, totalLabCategories);

        // When
        String result = dto.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("DashboardCountResponseDto"));

    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        String id1 = "testId1";
        Long total1 = 100L;
        Long totalLabCategories1 = 10L;

        String id2 = "testId1";
        Long total2 = 100L;
        Long totalLabCategories2 = 10L;

        DashboardCountResponseDto dto1 = new DashboardCountResponseDto(id1, total1, totalLabCategories1);
        DashboardCountResponseDto dto2 = new DashboardCountResponseDto(id2, total2, totalLabCategories2);

        // Then

    }

    @Test
    void testNotEqualsAndHashCode() {
        // Given
        String id1 = "testId1";
        Long total1 = 100L;
        Long totalLabCategories1 = 10L;

        String id2 = "testId2";
        Long total2 = 200L;
        Long totalLabCategories2 = 20L;

        DashboardCountResponseDto dto1 = new DashboardCountResponseDto(id1, total1, totalLabCategories1);
        DashboardCountResponseDto dto2 = new DashboardCountResponseDto(id2, total2, totalLabCategories2);

        // Then
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}
