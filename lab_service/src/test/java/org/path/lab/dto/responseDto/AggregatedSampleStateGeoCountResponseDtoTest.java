package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregatedSampleStateGeoCountResponseDtoTest {

    @Test
    void testConstructorWithAllCountsSet() {
        // Given
        List<SampleStateCountResponseDto> data = Arrays.asList(
                new SampleStateCountResponseDto(),
                new SampleStateCountResponseDto()
        );
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();
        dto.setData(data);
        dto.setInTransitCount(100L);
        dto.setUnderTestingCount(200L);
        dto.setTestedCount(300L);
        dto.setRejectedCount(50L);

        // Then
        assertNotNull(dto);
        assertEquals(data, dto.getData());
        assertEquals(100L, dto.getInTransitCount());
        assertEquals(200L, dto.getUnderTestingCount());
        assertEquals(300L, dto.getTestedCount());
        assertEquals(50L, dto.getRejectedCount());
    }

    @Test
    void testConstructorWithZeroCounts() {
        // Given
        List<SampleStateCountResponseDto> data = Collections.emptyList();
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();
        dto.setData(data);
        dto.setInTransitCount(0L);
        dto.setUnderTestingCount(0L);
        dto.setTestedCount(0L);
        dto.setRejectedCount(0L);

        // Then
        assertNotNull(dto);
        assertEquals(data, dto.getData());
        assertEquals(0L, dto.getInTransitCount());
        assertEquals(0L, dto.getUnderTestingCount());
        assertEquals(0L, dto.getTestedCount());
        assertEquals(0L, dto.getRejectedCount());
    }

    @Test
    void testGetData() {
        // Given
        List<SampleStateCountResponseDto> data = Arrays.asList(
                new SampleStateCountResponseDto(),
                new SampleStateCountResponseDto()
        );
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();
        dto.setData(data);

        // When
        List<SampleStateCountResponseDto> result = dto.getData();

        // Then
        assertEquals(data, result);
    }

    @Test
    void testSetData() {
        // Given
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();
        List<SampleStateCountResponseDto> data = Arrays.asList(
                new SampleStateCountResponseDto(),
                new SampleStateCountResponseDto()
        );

        // When
        dto.setData(data);

        // Then
        assertEquals(data, dto.getData());
    }

    @Test
    void testSetDataWithEmptyList() {
        // Given
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();
        List<SampleStateCountResponseDto> data = Collections.emptyList();

        // When
        dto.setData(data);

        // Then
        assertEquals(data, dto.getData());
    }

    @Test
    void testGetAndSetInTransitCount() {
        // Given
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();

        // When
        dto.setInTransitCount(100L);

        // Then
        assertEquals(100L, dto.getInTransitCount());
    }

    @Test
    void testGetAndSetUnderTestingCount() {
        // Given
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();

        // When
        dto.setUnderTestingCount(200L);

        // Then
        assertEquals(200L, dto.getUnderTestingCount());
    }

    @Test
    void testGetAndSetTestedCount() {
        // Given
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();

        // When
        dto.setTestedCount(300L);

        // Then
        assertEquals(300L, dto.getTestedCount());
    }

    @Test
    void testGetAndSetRejectedCount() {
        // Given
        AggregatedSampleStateGeoCountResponseDto dto = new AggregatedSampleStateGeoCountResponseDto();

        // When
        dto.setRejectedCount(50L);

        // Then
        assertEquals(50L, dto.getRejectedCount());
    }
}
