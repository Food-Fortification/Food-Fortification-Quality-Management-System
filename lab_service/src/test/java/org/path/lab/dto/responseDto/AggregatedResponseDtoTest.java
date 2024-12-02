package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregatedResponseDtoTest {

    @Test
    void testConstructorWithData() {
        // Given
        List<String> data = Arrays.asList("Item1", "Item2", "Item3");

        // When
        AggregatedResponseDto<String> dto = new AggregatedResponseDto<>();
        dto.setData(data);

        // Then
        assertNotNull(dto);
        assertEquals(data, dto.getData());
    }

    @Test
    void testConstructorWithNoData() {
        // Given
        List<String> data = Collections.emptyList();

        // When
        AggregatedResponseDto<String> dto = new AggregatedResponseDto<>();
        dto.setData(data);

        // Then
        assertNotNull(dto);
        assertEquals(data, dto.getData());
    }

    @Test
    void testGetData() {
        // Given
        List<String> data = Arrays.asList("Item1", "Item2", "Item3");
        AggregatedResponseDto<String> dto = new AggregatedResponseDto<>();
        dto.setData(data);

        // When
        List<String> result = dto.getData();

        // Then
        assertEquals(data, result);
    }

    @Test
    void testSetData() {
        // Given
        AggregatedResponseDto<String> dto = new AggregatedResponseDto<>();
        List<String> data = Arrays.asList("Item1", "Item2", "Item3");

        // When
        dto.setData(data);

        // Then
        assertEquals(data, dto.getData());
    }

    @Test
    void testSetDataWithEmptyList() {
        // Given
        AggregatedResponseDto<String> dto = new AggregatedResponseDto<>();
        List<String> data = Collections.emptyList();

        // When
        dto.setData(data);

        // Then
        assertEquals(data, dto.getData());
    }
}
