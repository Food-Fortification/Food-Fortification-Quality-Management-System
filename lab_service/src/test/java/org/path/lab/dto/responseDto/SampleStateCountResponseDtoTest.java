package org.path.lab.dto.responseDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleStateCountResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        // Given
        SampleStateCountResponseDto sampleStateCountResponseDto = new SampleStateCountResponseDto();
        String id = "sampleId";
        Long inTransitCount = 10L;
        Long underTestingCount = 20L;
        Long testedCount = 30L;
        Long rejectedCount = 5L;

        // When
        sampleStateCountResponseDto.setId(id);
        sampleStateCountResponseDto.setInTransitCount(inTransitCount);
        sampleStateCountResponseDto.setUnderTestingCount(underTestingCount);
        sampleStateCountResponseDto.setTestedCount(testedCount);
        sampleStateCountResponseDto.setRejectedCount(rejectedCount);

        // Then
        assertEquals(id, sampleStateCountResponseDto.getId());
        assertEquals(inTransitCount, sampleStateCountResponseDto.getInTransitCount());
        assertEquals(underTestingCount, sampleStateCountResponseDto.getUnderTestingCount());
        assertEquals(testedCount, sampleStateCountResponseDto.getTestedCount());
        assertEquals(rejectedCount, sampleStateCountResponseDto.getRejectedCount());
    }
}
