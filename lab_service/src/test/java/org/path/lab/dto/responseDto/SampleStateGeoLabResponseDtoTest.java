package org.path.lab.dto.responseDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleStateGeoLabResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        // Given
        SampleStateGeoLabResponseDto sampleStateGeoLabResponseDto = new SampleStateGeoLabResponseDto();
        Long labId = 1L;
        String labName = "Lab1";
        String labAddress = "Address1";
        Long inTransitCount = 10L;
        Long underTestingCount = 20L;
        Long testedCount = 30L;
        Long rejectedCount = 5L;

        // When
        sampleStateGeoLabResponseDto.setLabId(labId);
        sampleStateGeoLabResponseDto.setLabName(labName);
        sampleStateGeoLabResponseDto.setLabAddress(labAddress);
        sampleStateGeoLabResponseDto.setInTransitCount(inTransitCount);
        sampleStateGeoLabResponseDto.setUnderTestingCount(underTestingCount);
        sampleStateGeoLabResponseDto.setTestedCount(testedCount);
        sampleStateGeoLabResponseDto.setRejectedCount(rejectedCount);

        // Then
        assertEquals(labId, sampleStateGeoLabResponseDto.getLabId());
        assertEquals(labName, sampleStateGeoLabResponseDto.getLabName());
        assertEquals(labAddress, sampleStateGeoLabResponseDto.getLabAddress());
        assertEquals(inTransitCount, sampleStateGeoLabResponseDto.getInTransitCount());
        assertEquals(underTestingCount, sampleStateGeoLabResponseDto.getUnderTestingCount());
        assertEquals(testedCount, sampleStateGeoLabResponseDto.getTestedCount());
        assertEquals(rejectedCount, sampleStateGeoLabResponseDto.getRejectedCount());
    }
}
