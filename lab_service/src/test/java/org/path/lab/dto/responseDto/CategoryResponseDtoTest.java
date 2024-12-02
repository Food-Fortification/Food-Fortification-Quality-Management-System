package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryResponseDtoTest {

    @Test
    void testConstructorWithAllFields() {
        // Given
        Long id = 1L;
        String name = "Category Name";
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();

        // When
        CategoryResponseDto dto = new CategoryResponseDto(id, name, sourceCategories);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(sourceCategories, dto.getSourceCategories());
    }

    @Test
    void testGetId() {
        // Given
        Long id = 1L;
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(id);

        // When
        Long result = dto.getId();

        // Then
        assertEquals(id, result);
    }

    @Test
    void testSetId() {
        // Given
        CategoryResponseDto dto = new CategoryResponseDto();
        Long id = 1L;

        // When
        dto.setId(id);

        // Then
        assertEquals(id, dto.getId());
    }

    @Test
    void testGetName() {
        // Given
        String name = "Category Name";
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setName(name);

        // When
        String result = dto.getName();

        // Then
        assertEquals(name, result);
    }

    @Test
    void testSetName() {
        // Given
        CategoryResponseDto dto = new CategoryResponseDto();
        String name = "Category Name";

        // When
        dto.setName(name);

        // Then
        assertEquals(name, dto.getName());
    }

    @Test
    void testGetSourceCategories() {
        // Given
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setSourceCategories(sourceCategories);

        // When
        Set<CategoryResponseDto> result = dto.getSourceCategories();

        // Then
        assertEquals(sourceCategories, result);
    }

    @Test
    void testSetSourceCategories() {
        // Given
        CategoryResponseDto dto = new CategoryResponseDto();
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();

        // When
        dto.setSourceCategories(sourceCategories);

        // Then
        assertEquals(sourceCategories, dto.getSourceCategories());
    }

    @Test
    void testAddSourceCategory() {
        // Given
        CategoryResponseDto dto = new CategoryResponseDto();
        CategoryResponseDto sourceCategory = new CategoryResponseDto(2L, "Source Category", new HashSet<>());
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();
        sourceCategories.add(sourceCategory);
        dto.setSourceCategories(sourceCategories);

        // When
        dto.getSourceCategories().add(sourceCategory);

        // Then
        assertTrue(dto.getSourceCategories().contains(sourceCategory));
    }

    @Test
    void testRemoveSourceCategory() {
        // Given
        CategoryResponseDto dto = new CategoryResponseDto();
        CategoryResponseDto sourceCategory = new CategoryResponseDto(2L, "Source Category", new HashSet<>());
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();
        sourceCategories.add(sourceCategory);
        dto.setSourceCategories(sourceCategories);

        // When
        dto.getSourceCategories().remove(sourceCategory);

        // Then
        assertFalse(dto.getSourceCategories().contains(sourceCategory));
    }



    @Test
    void testToString() {
        // Given
        Long id = 1L;
        String name = "Category Name";
        Set<CategoryResponseDto> sourceCategories = new HashSet<>();
        CategoryResponseDto dto = new CategoryResponseDto(id, name, sourceCategories);

        // When
        String result = dto.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("CategoryResponseDto"));

    }
}
